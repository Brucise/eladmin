package me.zhengjie.modules.iptv.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.iptv.domain.Order;
import me.zhengjie.modules.iptv.domain.Payment;
import me.zhengjie.modules.iptv.domain.PaymentTransaction;
import me.zhengjie.modules.iptv.domain.enums.*;
import me.zhengjie.modules.iptv.repository.OrderRepository;
import me.zhengjie.modules.iptv.repository.PaymentRepository;
import me.zhengjie.modules.iptv.repository.PaymentTransactionRepository;
import me.zhengjie.modules.iptv.service.bo.OrderBo;
import me.zhengjie.modules.iptv.service.dto.OrderDto;
import me.zhengjie.modules.iptv.service.OrderService;
import me.zhengjie.modules.iptv.service.dto.PayNotifyDto;
import me.zhengjie.modules.iptv.util.OrderUtil;
import me.zhengjie.modules.iptv.util.RsaUtil;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.RoleRepository;
import me.zhengjie.modules.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Value("${pay.url}")
    private String payUrl;
    @Value("${merchant}")
    private String merchant;
    @Value("${pageUrl}")
    private String pageUrl;
    @Value("${notifyUrl}")
    private String notifyUrl;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DeptRepository deptRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object createOrder(OrderDto orderDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String orderNo = OrderUtil.generateOrderNo();
//        String paymentNo = UUID.randomUUID().toString().replace("-", "").substring(0, 20).toUpperCase();
        orderDTO.setOrderNo(orderNo);
        orderDTO.setMerchant(merchant);
        orderDTO.setPageUrl(pageUrl);
        orderDTO.setNotifyUrl(notifyUrl);
        orderDTO.setSubject(orderDTO.getEmail());
        orderDTO.setName(orderDTO.getWhatsapp());
        User user = creatUser(orderDTO);
        Order order = new Order();
        order.setTotalAmount(new BigDecimal(orderDTO.getAmount()));
        order.setUserId(user.getId());
        order.setCurrency(orderDTO.getBankCode());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderNo(orderNo);
        orderRepository.save(order);

        OrderBo orderBo = new OrderBo();
        orderBo.setOrderNo(orderNo);

        BeanUtil.copyProperties(orderDTO, orderBo);
        Map<String, Object> orderMap = BeanUtil.beanToMap(orderBo);
        String sign = RsaUtil.sign(orderMap);
        orderBo.setSign(sign);
        try {
            String resp = HttpUtil.post(payUrl, JSONUtil.toJsonStr(orderBo));
            JSONObject resp_obj = JSONUtil.parseObj(resp);
            if (resp_obj.getInt("code") != 0) {
                throw new Exception("支付接口调用失败");
            }

            // 4. 创建支付记录
            Payment newPayment = new Payment();
            newPayment.setPaymentNo(resp_obj.getJSONObject("data").getStr("orderNo"));
            newPayment.setOrderId(order.getId());
            newPayment.setUserId(user.getId());
            newPayment.setAmount(order.getTotalAmount());
            newPayment.setCurrency(order.getCurrency());
            newPayment.setPaymentMethod(PaymentMethod.fromString(FunPayMethod.getByCode(Integer.parseInt(orderDTO.getBusinessCode())).name()));
            newPayment.setStatus(PaymentStatus.PENDING);
            newPayment.setCreatedAt(LocalDateTime.now());
            newPayment.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(newPayment);

            PaymentTransaction transaction = new PaymentTransaction();
            transaction.setPaymentId(newPayment.getId());
            transaction.setRequestData(JSONUtil.toJsonStr(orderBo));
//            transaction.setResponseData(resp);
            transaction.setStatus(PaymentTransactionStatus.PENDING);
            paymentTransactionRepository.save(transaction);
            return resp;
        } catch (Exception e) {
            // 标记订单为失败
            log.error("下单失败", e);
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            return null;
        }
    }

    /**
     * 用户下单时，自动创建一个账户，并分组
     *
     * @param orderDTO
     */
    private User creatUser(OrderDto orderDTO) {
        User user = userRepository.findByUsername(orderDTO.getName());
        if (ObjUtil.isNull(user)) {
            User newUser = new User();
            newUser.setUsername(orderDTO.getName());
            newUser.setPhone(orderDTO.getPhone());
            newUser.setEmail(orderDTO.getEmail());
            Role role = roleRepository.findByName("客户");
            Set<Role> roles = Stream.of(role).collect(Collectors.toSet());
            List<Dept> depts = deptRepository.findByName("客户组");
            newUser.setDept(depts.get(0));
            newUser.setRoles(roles);
            newUser.setEnabled(true);
            userRepository.save(newUser);
            return newUser;
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String notify(PayNotifyDto payNotifyDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        log.info("回调接口请求参数为:{}", JSONUtil.toJsonStr(payNotifyDto));
        Map<String, Object> payNotifyDtoMap = BeanUtil.beanToMap(payNotifyDto);
        String sign = RsaUtil.sign(payNotifyDtoMap);
        if (!StrUtil.equalsIgnoreCase(sign, payNotifyDto.getSign())) {
            log.error("sign 校验失败");
            return "FAIL";
//            throw new RuntimeException("notify sign 校验不通过");
        }
        // 1. 查询支付记录
        Optional<Payment> paymentOpt = paymentRepository.findByPaymentNo(payNotifyDto.getOrderNo());
        if (!paymentOpt.isPresent()) {
            throw new RuntimeException("支付记录不存在: " + payNotifyDto.getOrderNo());
        }
        Payment payment = paymentOpt.get();

        // 2. 查询支付交易记录
        Optional<PaymentTransaction> transactionOpt = paymentTransactionRepository.findByPaymentId(payment.getId())
                .stream()
                .filter(t -> t.getStatus().equals(PaymentTransactionStatus.PENDING))
                .findFirst();

        if (!transactionOpt.isPresent()) {
            throw new RuntimeException("找不到 PENDING 状态的交易记录: " + payNotifyDto.getOrderNo());
        }
        PaymentTransaction transaction = transactionOpt.get();

        transaction.setResponseData(JSONUtil.toJsonStr(payNotifyDto));

        // 4. 处理支付状态
        if ("SUCCESS".equalsIgnoreCase(payNotifyDto.getStatus())) {
            transaction.setStatus(PaymentTransactionStatus.SUCCESS);
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setAmount(payNotifyDto.getPayAmount());
            payment.setFee(payNotifyDto.getFee());
            payment.setPayTime(payNotifyDto.getPayTime());
            updateOrderStatus(payment.getOrderId(), OrderStatus.PAID);

        } else if ("FAIL".equalsIgnoreCase(payNotifyDto.getStatus())) {
            transaction.setStatus(PaymentTransactionStatus.FAILED);
            payment.setAmount(payNotifyDto.getPayAmount());
            payment.setFee(payNotifyDto.getFee());
            payment.setPayTime(payNotifyDto.getPayTime());
            payment.setStatus(PaymentStatus.FAILED);

        } else if ("REFUNDED".equalsIgnoreCase(payNotifyDto.getStatus())) {
            transaction.setStatus(PaymentTransactionStatus.SUCCESS);
            payment.setStatus(PaymentStatus.REFUNDED);
            payment.setAmount(payNotifyDto.getPayAmount());
            updateOrderStatus(payment.getOrderId(), OrderStatus.REFUNDED);
        }

        // 5. 保存数据
        paymentTransactionRepository.save(transaction);
        paymentRepository.save(payment);
        return "SUCCESS";
    }

    /**
     * 更新订单状态
     */
    private void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(newStatus);
            orderRepository.save(order);
        }
    }

}
