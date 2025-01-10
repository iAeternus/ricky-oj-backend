package org.ricky.common.cache;

import org.ricky.common.utils.MyObjectMapper;
import org.ricky.core.judge.domain.Judge;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.NON_FINAL;
import static java.time.Duration.ofDays;
import static org.ricky.common.cache.CacheConfiguration.CACHE_PREFIX;
import static org.ricky.common.constants.CommonConstants.*;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/2
 * @className JudgeCacheConfiguration
 * @desc
 */
@Configuration
public class JudgeCacheConfiguration {

    @Bean("problemRedisBuilderCustomizer")
    public RedisCacheManagerBuilderCustomizer redisBuilderCustomizer(MyObjectMapper objectMapper) {
        MyObjectMapper defaultObjectMapper = new MyObjectMapper();
        defaultObjectMapper.activateDefaultTyping(defaultObjectMapper.getPolymorphicTypeValidator(), NON_FINAL, PROPERTY);
        GenericJackson2JsonRedisSerializer defaultSerializer = new GenericJackson2JsonRedisSerializer(defaultObjectMapper);

        var judgeSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, Judge.class);
        // TODO add here...

        return builder -> builder.withCacheConfiguration(JUDGE_CACHE, defaultCacheConfig()
                        .prefixCacheNameWith(CACHE_PREFIX)
                        .serializeValuesWith(fromSerializer(judgeSerializer))
                        .entryTtl(ofDays(7)));
    }

}
