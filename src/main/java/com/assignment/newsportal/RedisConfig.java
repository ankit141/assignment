//package com.assignment.newsportal;
//
//import com.assignment.newsportal.other.CustomKeyGenerator;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//
//@Configuration
//public class RedisConfig {
//
//    private JedisPool jedisPool;
//
//    @Value("${spring.redis.host}")
//    private String redisHost;
//
//    @Value("${spring.redis.port}")
//    private Integer redisPort;
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration =
//                new RedisStandaloneConfiguration(redisHost, redisPort);
//        return new JedisConnectionFactory(redisStandaloneConfiguration);
//    }
//
//    @Bean("customKey")
//    public KeyGenerator keyGenerator() {
//        return new CustomKeyGenerator();
//    }
//
//    @Bean
//    public JedisPool getJedisPool() {
//
//        jedisPool = new JedisPool(getPoolConfig(), redisHost, redisPort);
//        return jedisPool;
//    }
//
//    private GenericObjectPoolConfig getPoolConfig() {
//        return new GenericObjectPoolConfig();
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }
//
//}
