package com.aptech.project.hotel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EnCode {

    private static final Logger logger = LoggerFactory.getLogger(EnCode.class);
    private static final String SALT = "ChoThemItMuoiVaoChoNoMan";

    public static String md5(String str) {
        byte[] defaultBytes = (str + SALT).getBytes();

        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
        if (algorithm != null) {
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte[] messageDigest = algorithm.digest();
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            str = hexString.toString();
        }
        return str;
    }

    private EnCode() { throw new IllegalStateException("EnCode class"); }
}
