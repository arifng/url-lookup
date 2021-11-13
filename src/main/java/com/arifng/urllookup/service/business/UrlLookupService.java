package com.arifng.urllookup.service.business;

import com.arifng.urllookup.service.cache.UrlCacheService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class UrlLookupService {
    protected UrlCacheService urlCacheService;

    @Autowired
    public void setUrlCacheService(UrlCacheService urlCacheService) {
        this.urlCacheService = urlCacheService;
    }

    public List<String> getMappedUrls(List<String> inputUrls) {
        return inputUrls
                .stream()
                .map(this::getMappedUrl)
                .collect(Collectors.toList());
    }

    private String getMappedUrl(String inputUrl) {
        String urlFromCache = urlCacheService.getFromCache(inputUrl);
        if(urlFromCache != null) {
            return urlFromCache;
        }

        return processAndLookup(inputUrl);
    }

    protected abstract String processAndLookup(String url);

    /**
     * This method will concat param with delimiter from array on range 0 to endIndex inclusive.
     *
     * Example 1 - array - ["gender=female", "tag=123", "tag=1234", "tag=5678"]
     * delimiter &
     * if endIndex = 2, then result will be - gender=female&tag=123&tag=1234
     * if endIndex = 1, then result will be - gender=female&tag=123
     * if endIndex = 0, then result will be - gender=female

     * Example 2 - array - ["Women", "Fashion", "Addidas"]
     * delimiter /
     * if endIndex = 1, then result will be - Women/Fashion
     * if endIndex = 0, then result will be - Women
     */
    protected String concatUptoIndex(String[] params,
                                     int endIndex,
                                     String delimiter) {
        return Arrays.stream(params, 0, endIndex + 1)
                .collect(Collectors.joining(delimiter));
    }
}
