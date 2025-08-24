package com.muneesh.security;

import org.springframework.stereotype.Component;

import java.security.*;
import java.util.Base64;
@Component
public class PasswordUtil {
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltbyte = new byte[16];
        random.nextBytes(saltbyte);
        return Base64.getEncoder().encodeToString(saltbyte);
    }

    public static String hashPassword(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Base64.getDecoder().decode(salt));
        byte[] hashed = md.digest(password.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(hashed);

    }

}
