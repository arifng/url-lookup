package com.arifng.urllookup.service.business;

import com.arifng.urllookup.data.Constants;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Rana on 02/07/2021.
 */
@Service
public class ParameterizedUrlLookupService extends UrlLookupService {
    /**
     * Input Url is pretty, Output should be parameterized
     */
    @Override
    protected String processAndLookup(String inputUrl) {
        String[] splitUrl = Arrays.stream(inputUrl.split(Constants.DELIMITER_SLASH))
                .filter(e -> e.length() > 0)
                .toArray(String[]::new);
        if(splitUrl.length == 1) {
            return inputUrl;
        }

        // Logic - already check whole url
        // Now split by '/' and exclude last part from url
        // and check resultant url
        // continue to the first part of the url
        for (int i = splitUrl.length - 2; i >= 0; i--) {
            String urlToCheck = Constants.DELIMITER_SLASH + concatUptoIndex(splitUrl, i, Constants.DELIMITER_SLASH) + Constants.DELIMITER_SLASH;
            String result = urlCacheService.getFromCache(urlToCheck);
            if(result != null) {
                // unmatched part added as query param
                return result +
                        (result.contains(Constants.DELIMITER_QUESTION_MARK) ? Constants.DELIMITER_AMPERSAND : Constants.DELIMITER_QUESTION_MARK) +
                        convertAsQueryParam(splitUrl, i + 1);
            }
        }

        return inputUrl;
    }

    /**
     * This method will convert array to string on range startIndex to array.length - 1 inclusive.
     * Example - array - ["Women", "Fashion", "Addidas"]
     * if startIndex = 2, then result will be - tag=Addidas
     * if startIndex = 1, then result will be - tag=Fashion&tag=Addidas
     */
    private String convertAsQueryParam(String[] splitParams, int startIndex) {
        return Arrays.stream(splitParams, startIndex, splitParams.length)
                .map(param -> "tag=" + param)
                .collect(Collectors.joining("&"));
    }
}
