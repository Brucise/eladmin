package me.zhengjie.modules.iptv.repository;


import me.zhengjie.modules.iptv.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 根据支付流水号查询支付记录
    Optional<Payment> findByPaymentNo(String paymentNo);

    // 根据订单 ID 查询支付记录
    Optional<Payment> findByOrderId(Long orderId);
}
