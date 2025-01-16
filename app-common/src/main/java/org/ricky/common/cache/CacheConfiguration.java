package org.ricky.common.cache;

import org.ricky.common.utils.MyObjectMapper;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.NON_FINAL;
import static java.time.Duration.ofDays;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/10/14
 * @className CacheConfiguration
 * @desc
 */
@Configuration(proxyBeanMethods = false)
public class CacheConfiguration {

    /**
     * 缓存名称前缀
     */
    public static final String CACHE_PREFIX = "Cache:";

    @Bean("commonRedisBuilderCustomizer")
    public RedisCacheManagerBuilderCustomizer redisBuilderCustomizer() {
        MyObjectMapper defaultObjectMapper = new MyObjectMapper();
        defaultObjectMapper.activateDefaultTyping(defaultObjectMapper.getPolymorphicTypeValidator(), NON_FINAL, PROPERTY);
        GenericJackson2JsonRedisSerializer defaultSerializer = new GenericJackson2JsonRedisSerializer(defaultObjectMapper);
        return builder -> builder.cacheDefaults(defaultCacheConfig()
                .prefixCacheNameWith(CACHE_PREFIX)
                .serializeValuesWith(fromSerializer(defaultSerializer))
                .entryTtl(ofDays(1)));
    }

}
