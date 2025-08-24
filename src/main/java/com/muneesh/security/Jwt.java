package com.muneesh.security;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Component;

import java.time.*;

import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Component
public class Jwt {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public static String generateToken(String email, String role,long expiryTimeMillis) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setExpiration(new Date(expiryTimeMillis))
                .signWith(key)
                .compact();
    }

    public static String generateemailToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date().from(Instant.now().plus(20, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    public static boolean validateToken(String Token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(Token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String extractRole(String Token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(Token)
                .getBody()
                .get("role", String.class);

    }

    public static long extractExpiry(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .getTime();
    }


}
