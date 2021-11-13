package com.arifng.urllookup.manager;

import com.arifng.urllookup.service.cache.UrlCacheService;
import com.arifng.urllookup.service.db.MappedUrlService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Rana on 02/07/2021.
 */
public class DbToCacheUrlCopyManager {
    private MappedUrlService mappedUrlService;
    private UrlCacheService urlCacheService;

    @Autowired
    public void setMappedUrlService(MappedUrlService mappedUrlService) {
        this.mappedUrlService = mappedUrlService;
    }

    @Autowired
    public void setUrlCacheService(UrlCacheService urlCacheService) {
        this.urlCacheService = urlCacheService;
    }

    /**
     * This method will call at the time of project start up & run once
     *
     * This method load all url mappings into cache.
     * As both way conversion is possible, so both url will be used as key and value
     * and vice verse.
     *
     * Assume that, there is no update for url in database, so it load only once.
     */
    public void start() {
        mappedUrlService.getAll().forEach(mappedUrl -> {
            urlCacheService.putInCacheIfAbsent(mappedUrl.getParameterizedUrl(), mappedUrl.getPrettyUrl());
            urlCacheService.putInCacheIfAbsent(mappedUrl.getPrettyUrl(), mappedUrl.getParameterizedUrl());
        });
    }
}
