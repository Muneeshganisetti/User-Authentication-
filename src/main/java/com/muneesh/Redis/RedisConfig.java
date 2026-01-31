package com.muneesh.Redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedisConfig {
    private static final String REDIS_HOST = "redis://127.0.0.1:6379";

    @Bean
    public RedissonClient redisClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(REDIS_HOST);
        return Redisson.create(config);
    }

}



