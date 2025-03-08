package me.zhengjie.modules.iptv.repository;


import me.zhengjie.modules.iptv.domain.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    // 根据支付 ID 查询所有交易记录
    List<PaymentTransaction> findByPaymentId(Long paymentId);

    // 根据交易状态查询
    List<PaymentTransaction> findByStatus(String status);
}

