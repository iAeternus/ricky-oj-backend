package org.ricky.common.cache;

import org.ricky.common.utils.MyObjectMapper;
import org.ricky.core.user.domain.User;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import static java.time.Duration.ofDays;
import static org.ricky.common.cache.CacheConfiguration.CACHE_PREFIX;
import static org.ricky.common.constants.CommonConstants.PROBLEMS_CACHE;
import static org.ricky.common.constants.CommonConstants.USER_CACHE;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/2
 * @className UserCacheConfiguration
 * @desc
 */
@Configuration
public class UserCacheConfiguration {

    @Bean("userRedisBuilderCustomizer")
    public RedisCacheManagerBuilderCustomizer redisBuilderCustomizer(MyObjectMapper objectMapper) {
        var userSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, User.class);
        // TODO add here...

        return builder -> builder.withCacheConfiguration(USER_CACHE, defaultCacheConfig()
                .prefixCacheNameWith(CACHE_PREFIX)
                .serializeValuesWith(fromSerializer(userSerializer))
                .entryTtl(ofDays(7)));
    }

}
