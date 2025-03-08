package me.zhengjie.modules.iptv.domain.enums;

public enum OrderStatus {
    PENDING,     // 待支付
    PAID,        // 已支付
    CANCELLED,   // 已取消
    REFUNDED;    // 已退款

    @Override
    public String toString() {
        return name();
    }
}

