package me.zhengjie.modules.iptv.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderUtil {
    public static String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        String random = String.format("%05d", new Random().nextInt(99999)); // 5位随机数
        return timestamp + random;
    }
}
