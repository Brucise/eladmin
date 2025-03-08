package me.zhengjie.modules.iptv.domain.enums;

public enum FunPayMethod {
    VISA(104, "VISA"),
    MASTER_CARD(105, "MASTER_CARD"),
    UNION_PAY(106, "UNION_PAY");
    private final int code;
    private final String name;

    FunPayMethod(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static FunPayMethod fromString(String funPayMethod) {
        for (FunPayMethod s : FunPayMethod.values()) {
            if (s.name().equalsIgnoreCase(funPayMethod)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid FunPayMethod : " + funPayMethod);
    }

    // 通过code查找枚举
    public static FunPayMethod getByCode(int code) {
        for (FunPayMethod method : FunPayMethod.values()) {
            if (method.code == code) {
                return method;
            }
        }
        throw new IllegalArgumentException("无效的支付方式编码: " + code);
    }
}
