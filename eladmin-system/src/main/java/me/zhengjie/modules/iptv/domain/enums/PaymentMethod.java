package me.zhengjie.modules.iptv.domain.enums;

import java.util.Arrays;

public enum PaymentMethod {
    ALIPAY,       // 支付宝
    WECHAT,       // 微信支付
    VISA,         // Visa 信用卡
    MASTERCARD,   // Mastercard 信用卡
    PAYPAL,       // PayPal
    STRIPE,       // Stripe
    BANK_TRANSFER; // 银行转账

    @Override
    public String toString() {
        return name();
    }
    // 通过 name 获取 Enum（大小写不敏感）
    public static PaymentMethod fromString(String name) {
        return Arrays.stream(PaymentMethod.values())
                .filter(method -> method.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment method: " + name));
    }

}
