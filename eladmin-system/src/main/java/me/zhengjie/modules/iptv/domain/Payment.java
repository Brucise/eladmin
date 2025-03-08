package me.zhengjie.modules.iptv.domain;


import lombok.Data;
import me.zhengjie.modules.iptv.domain.enums.PaymentMethod;
import me.zhengjie.modules.iptv.domain.enums.PaymentStatus;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_order_id", columnList = "order_id"),
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_payment_method", columnList = "payment_method")
})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 支付记录 ID

    @Column(name = "payment_no", length = 64, nullable = false, unique = true)
    private String paymentNo; // 支付流水号

    @Column(name = "order_id", nullable = false)
    private Long orderId; // 关联订单 ID

    @Column(name = "user_id", nullable = false)
    private Long userId; // 关联用户 ID

    @DecimalMin(value = "0.00", message = "金额不能为负数")
    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount; // 支付金额

    @Column(name = "currency", length = 10, nullable = false)
    private String currency; // 货币类型（如 USD, CNY）

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 32, nullable = false)
    private PaymentStatus status; // 支付状态 (PENDING, SUCCESS, FAILED, REFUNDED)

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 32, nullable = false)
    private PaymentMethod paymentMethod; // 支付方式 (ALIPAY, WECHAT, VISA, MASTERCARD)

    @Column(name = "transaction_id", length = 64)
    private String transactionId; // 支付网关返回的交易号（可为空）

    // 新增字段：手续费（保留2位小数）
    @DecimalMin(value = "0.00", message = "手续费不能为负数")
    @Column(name = "fee", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal fee = BigDecimal.ZERO;

    // 新增字段：支付完成时间（自动记录）
    @Column(name = "pay_time", updatable = false)
    private LocalDateTime payTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 支付发起时间

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 更新时间

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
