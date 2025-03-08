package me.zhengjie.modules.iptv.domain.enums;

public enum PaymentTransactionStatus {
    PENDING,     // 交易处理中
    SUCCESS,     // 交易成功
    FAILED,      // 交易失败
    CANCELLED;   // 交易取消

    @Override
    public String toString() {
        return name();
    }

    // 通过字符串获取 Enum（忽略大小写）
    public static PaymentTransactionStatus fromString(String status) {
        for (PaymentTransactionStatus s : PaymentTransactionStatus.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid payment transaction status: " + status);
    }
}
