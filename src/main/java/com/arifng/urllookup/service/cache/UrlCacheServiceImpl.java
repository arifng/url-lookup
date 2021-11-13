package com.arifng.urllookup.service.cache;

import com.arifng.urllookup.config.CustomProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by Rana on 02/07/2021.
 */
@Service
public class UrlCacheServiceImpl implements UrlCacheService {
    private CacheManager cacheManager;
    private Cache cache;

    private CustomProperty customProperty;
    @Autowired
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Autowired
    public void setCustomProperty(CustomProperty customProperty) {
        this.customProperty = customProperty;
    }

    @Override
    public void putInCache(String key, String value) {
        getCache().put(key, value);
    }

    @Override
    public void putInCacheIfAbsent(String key, String value) {
        getCache().putIfAbsent(key, value);
    }

    @Override
    public String getFromCache(String key) {
        return getCache().get(key, String.class);
    }

    private Cache getCache() {
        if (Objects.isNull(cache)) {
            cache = cacheManager.getCache(customProperty.getCacheRegionName());
        }

        return cache;
    }
}
