package me.zhengjie.modules.iptv.domain;

import javax.persistence.*;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "iptv_order", indexes = {
        @Index(name = "idx_order_no", columnList = "order_no", unique = true)
})
public class IptvOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id; // 主键ID

    @Column(name = "order_no", length = 64, nullable = false, unique = true)
    private String orderNo; // 商户订单号

    @Column(name = "pay_order_no", length = 64, nullable = false, unique = true)
    private String payOrderNo; // 支付订单号

    @Column(name = "merchant", length = 64, nullable = false)
    private String merchant; // 商户号

    @Column(name = "business_code", length = 64, nullable = false)
    private String businessCode; // 业务编码

    @Column(name = "whatsapp", length = 64, nullable = false)
    private String whatsapp; // WhatsApp

    @Column(name = "name", length = 64, nullable = false)
    private String name; // 客户名字

    @Column(name = "phone", length = 32, nullable = false)
    private String phone; // 手机号

    @Column(name = "email", length = 128, nullable = false)
    private String email; // 邮箱

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount; // 下单金额

    @Column(name = "fee", precision = 10, scale = 2, nullable = false)
    private BigDecimal fee; // 手续费

    @Column(name = "pay_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal payAmount; // 实际支付金额

    @Column(name = "notify_url", length = 255, nullable = false)
    private String notifyUrl; // 通知地址

    @Column(name = "page_url", length = 255, nullable = false)
    private String pageUrl; // 回跳地址

    @Column(name = "bank_code", length = 32, nullable = false)
    private String bankCode; // 银行编码

    @Column(name = "subject", length = 255, nullable = false)
    private String subject; // 备注

    @Column(name = "message", length = 255, nullable = false)
    private String message; // 成功原因/失败原因

    @Column(name = "status", length = 32, nullable = false)
    private String status;//订单状态

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime; // 创建时间

    @Column(name = "pay_time")
    private LocalDateTime payTime; // 实际支付时间

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime; // 更新时间

    @PrePersist
    protected void onCreate() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}

