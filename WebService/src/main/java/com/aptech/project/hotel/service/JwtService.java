package com.aptech.project.hotel.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class JwtService {
    private static Logger logger = LoggerFactory.getLogger(JwtService.class);
    public static final String USERNAME = "username";
    public static final String SECRET_KEY = "CaiNayKhongDuocSuaChuaCungKhongDuocDongVaoNheHeHeHe^^";
    public static final int EXPIRE_TIME = 36000000;

    public String generateTokenLogin(String username) {
        String token = null;
        try {
            // Create HMAC signer
            JWSSigner signer = new MACSigner(generateShareSecret());
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim(USERNAME, username);
            builder.expirationTime(generateExpirationDate());
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            // Apply the HMAC protection
            signedJWT.sign(signer);
            // Serialize to compact form, produces something like
            token = signedJWT.serialize();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return token;
    }

    private JWTClaimsSet getClaimsFromToken(String token) {
        JWTClaimsSet claims = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(generateShareSecret());
            if (signedJWT.verify(verifier)) {
                claims = signedJWT.getJWTClaimsSet();
            }
        }  catch (ParseException e) {
            logger.error(e.getMessage());
        } catch (JOSEException e) {
            logger.error(e.getMessage());
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME);
    }

    private Date getExpirationDateFromToken(String token) {
        JWTClaimsSet claims = getClaimsFromToken(token);
        return claims != null ? claims.getExpirationTime() : new Date();
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            JWTClaimsSet claims = getClaimsFromToken(token);
            if (claims != null) username = claims.getStringClaim(USERNAME);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return username;
    }

    private byte[] generateShareSecret() {
        return SECRET_KEY.getBytes();
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateTokenLogin(String token) {
        return !isTokenExpired(token);
    }
}
