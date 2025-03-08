package me.zhengjie.modules.iptv.domain;

import lombok.Data;
import me.zhengjie.modules.iptv.domain.enums.PaymentTransactionStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment_transactions", indexes = {
        @Index(name = "idx_payment_id", columnList = "payment_id"),
        @Index(name = "idx_status", columnList = "status")
})
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 交易记录 ID

    @Column(name = "payment_id", nullable = false)
    private Long paymentId; // 关联支付 ID

    @Lob
    @Column(name = "request_data", nullable = false, columnDefinition = "TEXT")
    private String requestData; // 支付网关请求数据 (JSON)

    @Lob
    @Column(name = "response_data", columnDefinition = "TEXT")
    private String responseData; // 支付网关响应数据 (JSON)

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 32, nullable = false)
    private PaymentTransactionStatus status; // 交易状态 (PENDING, SUCCESS, FAILED)

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 交易创建时间

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 交易更新时间

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
