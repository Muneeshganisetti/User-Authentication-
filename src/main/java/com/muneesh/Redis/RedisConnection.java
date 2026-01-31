//package com.muneesh.Redis;
//
//import jakarta.annotation.PostConstruct;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RedisConnection {
//
//    private final RedissonClient redissonClient;
//
//    public RedisConnection(RedissonClient redissonClient) {
//        this.redissonClient = redissonClient;
//    }
//
//    @PostConstruct
//    public void testRedis() {
//        try {
//            redissonClient.getBucket("spring-test").set("connected");
//            System.out.println("✅ Redis CONNECTED from Spring Boot");
//        } catch (Exception e) {
//            System.out.println("❌ Redis NOT connected from Spring Boot");
//            e.printStackTrace();
//        }
//    }
//}
//
