//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.els.tools;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.*;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.els.common.exception.ELSBootException;
import com.els.common.util.PasswordUtil;
import com.els.common.util.RedisUtil;
import com.els.common.util.SpringContextUtils;
import com.els.common.util.encryption.AESUtil;
import com.els.common.util.encryption.RsaEncryptUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LicenseUtil {
    private static final Logger log = LoggerFactory.getLogger(LicenseUtil.class);
    private static final String REDIS_KEY = "sys:license:alert";
    private static final String CHECK_RESULT = "checkResult";
    private static final String CHECK_TIME = "checkTime";
    private static final Map<String, Object> localMap = new ConcurrentHashMap(8);
    private static final long intervalTime = 86400000L;
    private static   String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANuXTbMPXb//U4Km05/W0doPl2aruQPcNcDrSBeZKqC1GVNU/YQpsd84AJ3Rm5Ue2LHNqzt1N5HgcbVSh6Tw6X9V8QgxI9MLk7YvPU9jtbeb8D14/gjY0PyLI5bzkbG8PHNCULLAQikfyB2mOx6+JG5rDqO9eEnAJUWu4d2O2kP3AgMBAAECgYAdMo8IQgDLKJ3n/1qgJCYfAne1FRwyoTMchaw+RwVd/PZzGBflXM3jykR6t4YiW8mgSqJ782gCI3+7BlA25DmHj8BXynzw3FjloWgu7s+mZYIJEinfFumFSyVf2MFiwcLq1tZLtlPevY/leq1Cd2zf1Stav0zFodS/ufjwt05TkQJBAPdKTezF+fZ1zLE6VSL7rEfeInQE9CaCsBLIOalUlIgANnSJwGs4NtEBjZRM5pICWkdzH5WVFTeelzTZz4z5g8sCQQDjUz+tdanOB3fKV1daF5tsQ/jvYb9NDSHaOYzv9Akxe7wxsBMKfV7n0P2iGJuRRYfFCL8Cl3D5vN4bTBLvg/MFAkEAx2siYBhfNUNV6G30DhwKmIIoIouEIDIvr1XMOiM9DFevklcr1sskWXP5KmjBX2mKSCgshkINNCcuJK6pUzQ79QJBANqZ/YwisC3OBKXxo7ChLvbHzqT0THAeBsXBvgQpmoWZRXpKpaTatt8ZnC26mC70vAD/zh8B3sRpmPSLDVKCgrkCQF22SuKEj6TdVMLa/ZFE+Juy0Vlx9tRwzwHCqOMRM+NxGNQTgkpf1MW5diYZC1wlHBLa5BnIc7WeMwgFJd3EK5k=";
    private  static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDbl02zD12//1OCptOf1tHaD5dmq7kD3DXA60gXmSqgtRlTVP2EKbHfOACd0ZuVHtixzas7dTeR4HG1Uoek8Ol/VfEIMSPTC5O2Lz1PY7W3m/A9eP4I2ND8iyOW85GxvDxzQlCywEIpH8gdpjseviRuaw6jvXhJwCVFruHdjtpD9wIDAQAB";

    @Resource
    private RedisUtil redisUtil;

    public LicenseUtil() {
    }

    public static void main(String[] args) {
        pwd();
        // 生成RSA密钥对
//        KeyPair keyPair = SecureUtil.generateKeyPair(AsymmetricAlgorithm.RSA_None.getValue());
//
//        // 获取公钥和私钥
//        PublicKey publicKeyd = keyPair.getPublic();
//        PrivateKey privateKeyd = keyPair.getPrivate();
//
//        // 打印公钥和私钥信息（可选）
//        String publicKey = Base64.encode(publicKeyd.getEncoded());
//        String privateKey = Base64.encode(privateKeyd.getEncoded());
//        System.out.println("公钥: " + publicKey);
//        System.out.println("私钥: " + privateKey);
//
//        // 创建JSON对象
        JSONObject jsonObject = new JSONObject();
        // 过期时间
        jsonObject.put("expireDate", DateUtil.offsetDay(new Date(), 30000));
        // 有效时间
        jsonObject.put("effectiveDate", DateUtil.beginOfDay(new Date()));
        // 预警天数
        jsonObject.put("alertDays", 1);
        // 预警email
        jsonObject.put("alertEmail", "594761355@qq.com");
//
//        // 设置签名算法和签名的私钥
//        Sign signOne = SecureUtil.sign(SignAlgorithm.MD5withRSA, privateKey, null);
//
//        // 签名
//        byte[] signData = signOne.sign(JSONUtil.toJsonStr(jsonObject));
//
//        // 将签名结果Base64加密成字符串传输
//        String signDataStr = Base64.encode(signData);
//
//        // 创建用于数据加密的RSA对象
//        RSA rsaForDataEncode = new RSA(null, publicKey);
//        byte[] encrypt = rsaForDataEncode.encrypt(JSONUtil.toJsonStr(jsonObject).getBytes(StandardCharsets.UTF_8), KeyType.PublicKey);
//        String encodeData = Base64.encode(encrypt);
//
//        System.out.println("签名数据: " + signDataStr);
//        System.out.println("加密数据: " + encodeData);
//
//        System.out.println("interface license: " + encodeData + "." + signDataStr);


////        System.out.println("license: "+signDataStr + "." + encodeData);
////
//////        bdabfcc9b9947134e4098265c663e640
//////        String encrypt = PasswordUtil.encrypt("100000_admin", "123456", "0kpNV6x5");
//////        System.out.println( encrypt);
//////         验签
//////         设置签名算法和签名的公钥
//        Sign signTwo = SecureUtil.sign(SignAlgorithm.MD5withRSA, null, publicKey);
//        // 还原签名为byte数组
//        byte[] signDataOrigin = Base64.decode(signDataStr);
//        byte[] decodeData = Base64.decode(encodeData);
//        RSA rsaForDataDecode = new RSA(null, publicKey);
//        byte[] decrypt = rsaForDataDecode.decrypt(decodeData, KeyType.PublicKey);
//        boolean verify = signTwo.verify(decrypt, signDataOrigin);
//        System.out.println(verify);
//        String dataOrgin = new String(decrypt, StandardCharsets.UTF_8);
//        System.out.println("dataOrgin = " + dataOrgin);

//        check(publicKey,signDataStr + "." + encodeData);

//        String license = "i9a9+dw8z6GuXOYJa0jPw8RwVB9WGUj95du4RqW41m4xkmvnn8p2scQTOhHf/kwia+azg9lz8E16czmzXemsVxdoGJbq7H+wsY7+ifW47lcD3brDx22juRmpNALYOsOfqs57/5Cy95XPS2lQ3DhEyqBlK1Ig6SD+VgSQaOpSdLQ=.ZAy9YTF5NpACmS7EMrK1nqp1YW+5Dly/rBa1oZNgwJCbVqfcYQMpFmsU0YcmzdmatNsVitx1VD+eugUogwh4zYJdfNkdGCw2i1zGQd6pGBeEV5nUelYSgXrIbjJixUqjhOus/201nVnQHB9SkL7dFCYtScLcjB+eTzyma/k1jPs=";
//        odcheck(publicKey,  encodeData+ "." + signDataStr);

//        oldrunAES();
//        getKey();
    }

    public static void getKey(){
        // 生成RSA密钥对
        KeyPair keyPair = SecureUtil.generateKeyPair(AsymmetricAlgorithm.RSA_ECB.getValue());

        // 获取公钥和私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 打印公钥和私钥信息（可选）
        System.out.println("公钥: " + Base64.encode(publicKey.getEncoded()));
        System.out.println("私钥: " + Base64.encode(privateKey.getEncoded()));
    }
    //
    public static void odcheck(String publicKey, String license) {
        String[] licenseArry = license.split("\\.");
        String aesEncodeStr = licenseArry[0];

        String signStr = licenseArry[1];
        byte[] bData = aesEncodeStr.getBytes();
        byte[] signed = Base64.decode(signStr);
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, null, publicKey);
        boolean verify = sign.verify(bData, signed);
        if (!verify) {
            throw new ELSBootException("License");
        }
    }


    public static void check(String publicKey, String license) {

        try {
            String[] licenseArry = license.split("\\.");
            String signDataStr = licenseArry[0]; // 验签的数据
            String signStr = licenseArry[1]; // 验签字符串

            // 验签
            // 设置签名算法和签名的公钥
            Sign signTwo = SecureUtil.sign(SignAlgorithm.MD5withRSA, null, publicKey);
            // 还原签名为byte数组
            byte[] signDataOrigin = Base64.decode(signDataStr);

            System.out.println("signDataOrigin : " + JSONObject.toJSONString(signDataOrigin));
            byte[] decodeData = Base64.decode(signStr);
            RSA rsaForDataDecode = new RSA(null, publicKey);
            byte[] decrypt = rsaForDataDecode.decrypt(decodeData, KeyType.PublicKey);
            boolean verify = signTwo.verify(decrypt, signDataOrigin);
            if (!verify) {
                throw new ELSBootException("当前 License 相关配置信息错误，请正确配置。");
            }

            String aesDecodeStr = new String(decrypt, StandardCharsets.UTF_8);

            log.info("aesDecodeStr:" + aesDecodeStr);
            JSONObject infoObj = JSONObject.parseObject(aesDecodeStr);
            log.info("License验证返回信息" + infoObj.toJSONString());
            Date expireDate = infoObj.getDate("expireDate");
            if (expireDate.compareTo(DateUtil.offsetDay(new Date(), -1)) < 0) {
                localMap.put("checkResult", "expire");
                localMap.put("checkTime", System.currentTimeMillis());
                return;
            }

            Date effectiveDate = infoObj.getDate("effectiveDate");
            if (effectiveDate.compareTo(DateUtil.offsetDay(new Date(), 0)) > 0) {
                localMap.put("checkResult", "effective");
                localMap.put("checkTime", System.currentTimeMillis());
                return;
            }

            localMap.put("checkResult", "success");
            localMap.put("checkTime", System.currentTimeMillis());
        } catch (Exception var17) {
            log.error("LicenseUtil_check_failed:", var17);
            throw new ELSBootException("当前 License 相关配置信息错误，请正确配置。");
        }

    }

    @Test
    public void runAES() {
        String license = "UcUrKR9NUDiguZAhuOgRYS3STUTz5UVtAzAgpfEu2d+NqIXNXDv2GusAuU+4Z+Nr8/MLSnaaQ1E9wZpQLMdEeaaUw7NoNGdn2RNv8HMpEZzlUEcE2+evgfTEnPXPMhGQw1p+UeNEDnC0mzcRI9mSGQ==.eMwz+SM2wYBiek8YOvIlwGVpRXs84HiZKT6PqTpDo7/mbxv5DBdCOi8V0AoHrV7HYqvFS2am4QnWQ5XSohdS3q1NOhAd7J5a3CwwC6oPQbWIoT0yXvcTnV6AxANLms0K4CGAck23JcindJ6vJir6ID6NFWmG72qIBgQ7PM8GmIw=";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+6A9ubk5CUHjPItGXVW55Pa6mlmvSmMN1ewwjM0o7gzdKS12XR7aa8ZI5ZRgazhSieZKwuyDNJlSd6ebuw47ec+7uFuZEgA53G/Yc6ZXuRwlrQAELuZzTzQZRvSDF/i6F1D7YafRegtUSYQXKMWyrF6yn1lR+kgGnU24uRcksWQIDAQAB";
        String secret = "647e8517d4f24c7b9fd38c443e78db0e";
        String[] licenseArry = license.split("\\.");
        String aesEncodeStr = licenseArry[0];
        String signStr = licenseArry[1];
        byte[] bData = aesEncodeStr.getBytes();
        byte[] signed = Base64.decode(signStr);
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, (String) null, publicKey);
        boolean verify = sign.verify(bData, signed);
        if (!verify) {
            throw new ELSBootException("当前 License 相关配置信息错误，请正确配置。");
        } else {
            String aesDecodeStr = AESUtil.decrypt(aesEncodeStr, secret);
            if (aesDecodeStr == null) {
                aesDecodeStr = Base64.decodeStr(aesEncodeStr);
            }

            JSONObject infoObj = JSONObject.parseObject(aesDecodeStr);
            System.out.println(infoObj.toJSONString());
        }
    }


    public static void oldrunAES() {
        String license = "UcUrKR9NUDiguZAhuOgRYS3STUTz5UVtAzAgpfEu2d+NqIXNXDv2GusAuU+4Z+Nr8/MLSnaaQ1E9wZpQLMdEeaaUw7NoNGdn2RNv8HMpEZzlUEcE2+evgfTEnPXPMhGQw1p+UeNEDnC0mzcRI9mSGQ==.eMwz+SM2wYBiek8YOvIlwGVpRXs84HiZKT6PqTpDo7/mbxv5DBdCOi8V0AoHrV7HYqvFS2am4QnWQ5XSohdS3q1NOhAd7J5a3CwwC6oPQbWIoT0yXvcTnV6AxANLms0K4CGAck23JcindJ6vJir6ID6NFWmG72qIBgQ7PM8GmIw=";
//        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+6A9ubk5CUHjPItGXVW55Pa6mlmvSmMN1ewwjM0o7gzdKS12XR7aa8ZI5ZRgazhSieZKwuyDNJlSd6ebuw47ec+7uFuZEgA53G/Yc6ZXuRwlrQAELuZzTzQZRvSDF/i6F1D7YafRegtUSYQXKMWyrF6yn1lR+kgGnU24uRcksWQIDAQAB";
        String[] licenseArry = license.split("\\.");
        String aesEncodeStr = licenseArry[0];
        String signStr = licenseArry[1];
        byte[] bData = aesEncodeStr.getBytes();
        byte[] signed = Base64.decode(signStr);
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, null, publicKey);
        boolean verify = sign.verify(bData, signed);
        if (!verify) {

            throw new ELSBootException("License");
        }
//        String aesDecodeStr = AESUtil.decrypt(aesEncodeStr, secret);
//        if (aesDecodeStr == null)
//            aesDecodeStr = Base64.decodeStr(aesEncodeStr);
//        JSONObject infoObj = JSONObject.parseObject(aesDecodeStr);
//        System.out.println(infoObj.toJSONString());
    }

    public static void pwd() {


//
        // 生成RSA密钥对
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 要签名的数据
        JSONObject jsonObject = new JSONObject();
        // 过期时间
        jsonObject.put("expireDate", DateUtil.offsetDay(new Date(), 30000));
        // 有效时间
        jsonObject.put("effectiveDate", DateUtil.beginOfDay(new Date()));
        // 预警天数
        jsonObject.put("alertDays", 1);
        // 预警email
        jsonObject.put("alertEmail", "594761355@qqcom");
        String data = JSONUtil.toJsonStr(jsonObject);

        System.out.println("公钥: " + Base64.encode(publicKey.getEncoded()));
        System.out.println("私钥: " + Base64.encode(privateKey.getEncoded()));

        // 签名
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, Base64.encode(privateKey.getEncoded()), null);
        byte[] signData = sign.sign(data.getBytes(StandardCharsets.UTF_8));
        String signDataStr = Base64.encode(signData);

        // 验证签名
        Sign verifySign = SecureUtil.sign(SignAlgorithm.MD5withRSA, null, Base64.encode(publicKey.getEncoded()));
        boolean verified = verifySign.verify(data.getBytes(StandardCharsets.UTF_8), Base64.decode(signDataStr));
        System.out.println("加密数据: " + data);
        System.out.println("签名数据: " + signDataStr);
        System.out.println("签名验证结果: " + verified);

        odcheck(Base64.encode(publicKey.getEncoded()),data+"."+signDataStr);
    }
}
