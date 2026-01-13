package com.app.guess.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.app.guess.constant.ErrorCode;
import com.app.guess.exception.AppException;

public class VnPayUtils {
    private static final String algorithm = "HmacSHA512";

    public static String sign(String key, String value) {
        try {
            Mac hmac512 = Mac.getInstance(algorithm);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), algorithm);
            hmac512.init(secretKey);
            byte[] bytes = hmac512.doFinal(value.getBytes());
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception e) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

    }
}
