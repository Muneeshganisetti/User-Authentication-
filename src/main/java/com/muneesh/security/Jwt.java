package com.muneesh.security;

import com.muneesh.service.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.*;
import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Slf4j
@Component
public class Jwt {

    private final RedisService redisService;

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public Jwt(RedisService redisService) {
        this.redisService = redisService;
    }


    public String generateToken(String email, String role, long expiryTimeMillis) {
        String sessionId = UUID.randomUUID().toString();
        String token = Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("sessionId", sessionId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiryTimeMillis))
                .signWith(key)
                .compact();
        try {
            redisService.setLogin(email, sessionId);
        } catch (Exception e) {
            throw new RuntimeException("not saved in redis" + e);
        }
        return token;
    }

    public static String generateemailToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date().from(Instant.now().plus(20, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    public String validateToken(String Token) {
        try {
            Claims claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(Token).getBody();

            String tokenSessionId = claim.get("sessionId", String.class);
            String email = claim.getSubject();
            String sessionIdInRedis = redisService.getLogin(email);
            if (sessionIdInRedis == null||!sessionIdInRedis.equals(tokenSessionId)) {
                log.error("invalid session for email {}", email);
                throw new Exception("invalid session");
            }
            return email;
        } catch (Exception e) {
            throw new RuntimeException(e);
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
