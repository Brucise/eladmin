package me.zhengjie.modules.iptv.domain.enums;

public enum PaymentStatus {
    PENDING,    // 待支付
    SUCCESS,    // 支付成功
    FAILED,     // 支付失败
    REFUNDED;   // 已退款

    @Override
    public String toString() {
        return name();
    }
}
