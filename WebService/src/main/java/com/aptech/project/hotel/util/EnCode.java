package com.aptech.project.hotel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnCode {

    private static final Logger logger = LoggerFactory.getLogger(EnCode.class);
    private static final String salt = "ChoThemItMuoiVaoChoNoMan";

    public static String md5(String str) {
        byte[] defaultBytes = (str + salt).getBytes();

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

    public static void main(String... args) throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(format.parse ( "9999-12-31 23:59:59" ).getTime());
//        System.out.println(new Date());
        System.out.println(EnCode.md5("1"));
    }
}
