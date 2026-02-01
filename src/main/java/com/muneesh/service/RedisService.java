package com.muneesh.service;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedissonClient redissonClient;


    public void setLogin(String email, String sessionId) {
        RBucket<String> set = redissonClient.getBucket(email);
        set.set(sessionId);
        log.info("id saved in reids{}",sessionId);
    }

    public String getLogin(String email) {
        RBucket<String> get = redissonClient.getBucket(email);
        log.info("getting id from redis {}",email);
        return get.get();
    }
}
