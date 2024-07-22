package ru.agniaendie.productservice.config

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisSentinelConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration


@Configuration
@RequiredArgsConstructor
class RedisConfig(
    @Value("\${spring.cache.redis.time-to-live}") var redisTimeToLive: Long,
    @Value("\${spring.data.redis.timeout}") var redisCommandTimeout: Duration,
    val redisProperties: RedisProperties
) : CachingConfigurer {
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val redisSentinelConfiguration = RedisSentinelConfiguration()
        redisSentinelConfiguration.master(redisProperties.sentinel.master)
        redisProperties.sentinel.nodes.forEach { s -> redisSentinelConfiguration.sentinel(s, redisProperties.port) }
        redisSentinelConfiguration.password = RedisPassword.of(redisProperties.password)
        val factory = LettuceConnectionFactory(redisSentinelConfiguration)
        return factory
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory()
        return redisTemplate
    }
}