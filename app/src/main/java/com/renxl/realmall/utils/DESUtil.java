package com.renxl.realmall.utils;

import java.security.Key;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by renxl
 * On 2017/5/18 11:16.
 */

public class DESUtil {
    private static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    /**
     * DES 算法，加密
     *
     * @param key  加密私钥，长度不少于 8 位
     * @param data 待加密字符串
     * @return 加密后的字节数组，一般结合 Base64 编码使用
     */
    public static String encode(String key, String data) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return byte2String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 二进制转字符串
     *
     * @param b 二进制数组
     * @return 转换后的字符串
     */
    private static String byte2String(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase(Locale.CHINA);
    }
}
