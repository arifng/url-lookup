package com.arifng.urllookup.service.cache;

public interface UrlCacheService {
    void putInCache(String key, String value);

    void putInCacheIfAbsent(String key, String value);

    String getFromCache(String key);
}
