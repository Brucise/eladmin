package me.zhengjie.modules.iptv.util;

import cn.hutool.core.convert.Convert;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

import javax.crypto.Cipher;
import javax.sound.midi.Soundbank;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateUtil;

import java.time.LocalDateTime;

public class RsaUtil {

    private static final Logger log = LoggerFactory.getLogger(RsaUtil.class);

    //加密算法RSA
    private static final String KEY_ALGORITHM = "RSA";
    //RSA最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;
    //RSA最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final String CHARSET = "UTF-8";

    public static Map<String, String> genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024);
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        Map<String, String> retMap = new HashMap<>();
        retMap.put("pubKey", publicKeyString);
        retMap.put("priKey", privateKeyString);
        return retMap;
    }

    public static Map<String, String> genKeyPair_2() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024);
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        Map<String, String> retMap = new HashMap<>();
        retMap.put("pubKey", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmaQkZDjGjHHG5Y5F21fiStB8pv5OeJaCfEipAwIFr4Bz2CqZYAnvxrWa0nmUA51guaj9t97jNw4C/jiLZ3zOgBBlSPwFXfX56Nic9dUM2nvMUhC43zLNmRX1x29w47lzg1PGXfYYeB8KoF7fvS0xHcjAH9BONbzg0LYCPnL+SiQIDAQAB");
        retMap.put("priKey", "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKZpCRkOMaMccbljkXbV+JK0Hym/k54loJ8SKkDAgWvgHPYKplgCe/GtZrSeZQDnWC5qP233uM3DgL+OItnfM6AEGVI/AVd9fno2Jz11Qzae8xSELjfMs2ZFfXHb3DjuXODU8Zd9hh4HwqgXt+9LTEdyMAf0E41vODQtgI+cv5KJAgMBAAECgYAG6ndB/vtZyl/WPZNuZebGag1K1eG+Vn/+Eb+HILkAO7iDEomRP3aD4m9R8wHtmgUUgL6Rb78oG1zpbnB3r4qx+soc1Fp7ISZJzQVkhJdq7HWnqODNNqs6XxQZGR/tjywCQXrkB02HRTQDEf4eIRm2WFqpCZJTu7bWcVTmOwzL5QJBAOwKfNNi5Yh2z6Pr+5d22+MLlnXxGmJC935f4jImSybzv624+yb4Av9Vy0rhNaRkEdjjfyyFiG9H/vb3M9vSL7MCQQC0e0W6IoUKa61kqLTBk2D68vq7Q5d3PjzfAflRs4vMtvn5x0XBFqbipUtFOTdcKva2b4ZYZFJRsCdpaooIg7bTAkEAytkkdwFZotIAFa5ac8tIorE1p7wA4YsNaIR8Pn7cPOhixKfg5pdi9A3F/F7Ym6MIF21CwH8tRf0IZzMAVRwnswJBAIXT8rw25Jf5iDVfs8jmU79BdRJu6F2PVOu4NvuSO1OtSmcgkGTBOzZMgyftaVN6uD5HLENXAIN6L39HdNsjb+kCQB6895oOZ/yxhPIDMGP2d1lZxILQjS6r6/FWpfRSHLaag0pa6EK8bab/8CxL104qnXqg3KDEf/CqDo/EJpfnf7U=");
        return retMap;
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * @param signSrc    源
     * @param cipherText 待解密数据
     * @param publickey  公钥
     * @return
     * @throws Exception
     */
    public static boolean verifySignByPub(String signSrc, String cipherText, String publickey) {
        Boolean verifySign = false;
        try {
            String decryptSign = decryptByPublic(cipherText, getPublicKey(publickey));
            if (signSrc.equalsIgnoreCase(decryptSign)) {
                verifySign = true;
            }
        } catch (Exception e) {
            log.error("公钥解密时出现异常:{}", e.getMessage());
        }
        return verifySign;
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */
    public static String encryptByPrivate(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] bytes = rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength());
            return Base64.encodeBase64URLSafeString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("私钥加密字符串[" + data + "]时异常", e);
        }
    }

    /**
     * 私钥加密
     *
     * @param data       源
     * @param privateKey 私钥
     * @return
     */
    public static String encryptByPrivate(String data, String privateKey) {
        try {
            //获取私钥
            RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), rsaPrivateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("私钥加密字符串[" + data + "]时异常", e);
        }
    }


    /**
     * 公钥解密
     *
     * @param data      源
     * @param publicKey 公钥
     * @return
     */
    public static String decryptByPublic(String data, String publicKey) {
        try {
            RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), rsaPublicKey.getModulus().bitLength()), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("公钥解密字符串[" + data + "]时异常", e);
        }
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String decryptByPublic(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("公钥解密字符串[" + data + "]时异常", e);
        }
    }

    /**
     * 数据分段加密
     *
     * @param cipher
     * @param opmode
     * @param datas
     * @param keySize
     * @return
     * @throws Exception
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) throws Exception {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        byte[] resultDatas = null;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
            resultDatas = out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        } finally {
            out.close();
        }
        return resultDatas;
    }


    /**
     * 数据排序
     *
     * @param keys
     * @return
     */
    public static String[] getUrlParam(String[] keys) {

        for (int i = 0; i < keys.length - 1; i++) {
            for (int j = 0; j < keys.length - i - 1; j++) {
                String pre = keys[j];
                String next = keys[j + 1];
                if (isMoreThan(pre, next)) {
                    String temp = pre;
                    keys[j] = next;
                    keys[j + 1] = temp;
                }
            }
        }
        return keys;
    }

    /**
     * 签名
     *
     * @return
     */
    public static String sign(Map<String, Object> params) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String queryString = generateSignature(params);
        Map<String, String> retMap = RsaUtil.genKeyPair();
        String pubKey = retMap.get("pubKey");
        String priKey = retMap.get("priKey");
        String priCipherText = RsaUtil.encryptByPrivate(queryString, priKey);
        priCipherText = URLEncoder.encode(priCipherText, "UTF-8");
        return priCipherText;
    }

    public static String sign(Map<String, Object> params, String priKey) throws UnsupportedEncodingException {
        String queryString = generateSignature(params);
        String priCipherText = RsaUtil.encryptByPrivate(queryString, priKey);
        priCipherText = URLEncoder.encode(priCipherText, "UTF-8");
        return priCipherText;
    }

    /**
     * 构造参数字典序
     *
     * @param params
     * @return
     */
    public static String generateSignature(Map<String, Object> params) {
        // 1. 过滤不参与签名的字段和空值
        Map<String, String> filteredParams = params.entrySet().stream()
                .filter(entry ->
                        !entry.getKey().equals("sign") &&
                                !entry.getKey().equals("fee") &&
                                entry.getValue() != null
                )
                // 2. 使用Hutool安全类型转换（支持Boolean/Number/Collection等类型）
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey(),

                        formatValue(entry.getValue()) // 处理值 // 非String类型转String，null转空字符串
                ))
                // 3. 二次过滤空字符串
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        // 4. ASCII排序 & 拼接QueryString
        return filteredParams.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // 按字段名ASCII排序
                .map(entry -> {
                    return entry.getKey() + "=" + entry.getValue();
//                    // 5. URL编码处理特殊字符（如&=等）
//                    String encodedKey = URLUtil.encode(entry.getKey(), StandardCharsets.UTF_8);
//                    String encodedValue = URLUtil.encode(entry.getValue(),StandardCharsets.UTF_8);
//                    return encodedKey + "=" + encodedValue;
                })
                .collect(Collectors.joining("&"));
    }

    /**
     * 格式化值
     */
    private static String formatValue(Object value) {
        if (value instanceof Date) {
            // 如果是 Date 类型，格式化为字符串
            return DateUtil.format((Date) value, "yyyy-MM-dd HH:mm:ss");
        } else if (value instanceof LocalDateTime) {
            // 如果是 LocalDateTime 类型，格式化为字符串
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return ((LocalDateTime) value).format(formatter);
        } else {
            // 其他类型，直接转换为字符串
            return Convert.toStr(value, "");
        }
    }

    private static boolean isMoreThan(String pre, String next) {
        if (null == pre || null == next || "".equals(pre) || "".equals(next)) {
            return false;
        }

        char[] c_pre = pre.toCharArray();
        char[] c_next = next.toCharArray();

        int minSize = Math.min(c_pre.length, c_next.length);

        for (int i = 0; i < minSize; i++) {
            if ((int) c_pre[i] > (int) c_next[i]) {
                return true;
            } else if ((int) c_pre[i] < (int) c_next[i]) {
                return false;
            }
        }
        if (c_pre.length > c_next.length) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> retMap = RsaUtil.genKeyPair_2();
        String pubKey = retMap.get("pubKey");
        String priKey = retMap.get("priKey");
        System.out.println("生成公钥\n" + pubKey);
        System.out.println("公钥长度\n" + pubKey.length());

        try {
            RSAPublicKey publicKey = RsaUtil.getPublicKey(pubKey);
            if (publicKey != null) {
                System.out.println("校验公钥合法性\n" + true);
            }
        } catch (Exception e) {
            System.out.println("校验公钥合法性\n" + false);
        }

        System.out.println("生成私钥\n" + priKey);
        System.out.println("私钥长度\n" + priKey.length());


        String message = "amount=200&bankCode=BANK&businessCode=104&email=15998765432@163.com&merchant=1009&name=test&notifyUrl=https://api.funpay.tv/test&orderNo=1722321107781&pageUrl=https://api.funpay.tv/test&phone=15998765432&subject=测试测试";
        System.out.println("明文\n" + message);
        System.out.println("明文长度\n" + message.length());

        String priCipherText = RsaUtil.encryptByPrivate(message, priKey);
        System.out.println("私钥加密后密文\n" + priCipherText);

        priCipherText = URLEncoder.encode(priCipherText, "UTF-8");
        System.out.println("URL编码后" + priCipherText + "\n");

        priCipherText = URLDecoder.decode(priCipherText, "UTF-8");
        System.out.println("URL解码后" + priCipherText + "\n");

        String priPlainText = RsaUtil.decryptByPublic(priCipherText, pubKey);
        System.out.println("公钥解密后明文\n" + priPlainText);

        boolean verifySign = RsaUtil.verifySignByPub(message, priCipherText, pubKey);
        System.out.println("验签结果\n" + verifySign);


        String queryString = "amount=9.00&businessCode=104&merchant=1009&merchantOrderNo=2025030922020800094&message=success&orderNo=202503091348415591442833408&payAmount=9.00&status=success";
        String sign = "agW76uKzkaL3vBKHb8_N6IFyy6ihLhFtX-gs0uS9V6YbHB7TyXm8vCU0BjjkuYa4uTzKB3wnoYKZBEZDT2N0Jz12PUtLlOZMsZLGNf8HH7p6UTQ9NYXbM-Wd2_9BVZDRkprUpRSWmQb8iK2TS3n_yrDsOwtcBDMJtjkViLPxUqc6jC-ilrYLpqSdyq084u7dtV8sRK6FXsepVuvI9hPFhQEDFme3Be8GMOmNjM7QbTZPszhiefR5Xi7kgpzAMEr2D4euadN340fRBf3746zADPB2okOaDzc37-AtiHNixHH4P4kn3sVfJbWxkzFG0tDox4y85ZB5eLVGUF5GSmV5jA";
        boolean verifySign_1 = RsaUtil.verifySignByPub(queryString, sign, pubKey);
        System.out.println("验签结果_1\n" + verifySign_1);

    }
}
