package me.zhengjie.modules.iptv.domain;

import lombok.Data;
import me.zhengjie.modules.iptv.domain.enums.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_order_status", columnList = "status"),
        @Index(name = "idx_created_at", columnList = "created_at")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键ID

    @Column(name = "order_no", length = 64, nullable = false, unique = true)
    private String orderNo; // 商户订单号

    @Column(name = "user_id", nullable = false)
    private Long userId; // 用户ID
    @DecimalMin(value = "0.00", message = "金额不能为负数")
    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount; // 订单总金额

    @Column(name = "currency", length = 10, nullable = false)
    private String currency; // 货币类型

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 32, nullable = false)
    private OrderStatus status; // 订单状态 (PENDING, PAID, CANCELLED, REFUNDED)

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 订单创建时间

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 订单更新时间

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
