package me.zhengjie.modules.pay.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.pay.service.dto.OrderDto;
import me.zhengjie.modules.pay.service.FunPayService;
import me.zhengjie.modules.pay.util.RsaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FunPayServiceImpl implements FunPayService {
    @Value("${pay.url}")
    private String payUtl;

    @Override
    public Object createOrder(OrderDto orderDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String payUrl = "";
        Map<String, Object> orderMap = BeanUtil.beanToMap(orderDTO);
        String sign = RsaUtil.sign(orderMap);
        orderDTO.setSign(sign);
        String resp = HttpUtil.post(payUrl, JSONUtil.toJsonStr(orderDTO));
        System.out.println(resp);
        return resp;
    }
}
