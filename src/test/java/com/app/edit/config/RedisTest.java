package com.app.edit.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@DataRedisTest
@ActiveProfiles("prod")
class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @DisplayName("ElastiCache Redis 연결을 테스트 한다.")
    @Test
    void redisConnectionTest() {
        redisTemplate.opsForValue().set("hello", "colt!", Duration.ofSeconds(600L));
        String hello = redisTemplate.opsForValue().get("hello");
        assertThat(redisTemplate.hasKey("hello")).isTrue();
        assertThat(hello).isEqualTo("colt!");
    }
}