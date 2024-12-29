package org.ricky.common.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;


/**
 * @author Ricky
 * @version 1.0
 * @date 2024/10/14
 * @className CacheClearer
 * @desc
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheClearer {

    @Caching(evict = {
            // @CacheEvict(value = ORDER_CACHE, allEntries = true),
            // @CacheEvict(value = ORDER_DETAIL_CACHE, allEntries = true),
            // @CacheEvict(value = STATION_CACHE, allEntries = true),
            // @CacheEvict(value = CARRIAGE_CACHE, allEntries = true),
            // @CacheEvict(value = SEAT_CACHE, allEntries = true),
            // @CacheEvict(value = TRAIN_CACHE, allEntries = true),
            // @CacheEvict(value = TRIP_CACHE, allEntries = true),
            // @CacheEvict(value = USER_CACHE, allEntries = true),
    })
    public void evictAllCache() {
        log.info("Evicted all cache.");
    }
}