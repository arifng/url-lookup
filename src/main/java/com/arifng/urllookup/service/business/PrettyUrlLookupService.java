package com.arifng.urllookup.service.business;

import com.arifng.urllookup.data.Constants;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Rana on 03/07/2021.
 */
@Service
public class PrettyUrlLookupService extends UrlLookupService {
    /**
     * Input url is parameterized, output should be pretty.
     */
    @Override
    protected String processAndLookup(String inputUrl) {
        String[] splitUrl = inputUrl.split(Constants.PARAM_URL_REGEX);

        // if noQueryParams return true, that means
        // the url don't have query params
        // in that case conversion is not possible.
        if(noQueryParams(splitUrl)) {
            return inputUrl;
        }

        String firstPart = splitUrl[0];
        String secondPart = splitUrl[1];

        String[] splitParams = secondPart.split(Constants.DELIMITER_AMPERSAND);
        for (int i = splitParams.length - 2; i >= 0; i--) {
            String urlToCheck = firstPart + Constants.DELIMITER_QUESTION_MARK +
                    concatUptoIndex(splitParams, i, Constants.DELIMITER_AMPERSAND);
            String result = urlCacheService.getFromCache(urlToCheck);
            if(result != null) {
                return result + Constants.DELIMITER_QUESTION_MARK +
                        getAsQueryParams(splitParams, i + 1);
            }
        }

        // no url found with params, now try it with left part of '?'
        // example - /product?tag=123, try with only /product now
        String result = urlCacheService.getFromCache(firstPart);
        return result == null ? inputUrl :
                result + Constants.DELIMITER_QUESTION_MARK + getAsQueryParams(splitParams, 0);
    }

    /**
     * This method will check if the url has any query params or not
     */
    private boolean noQueryParams(String[] splitUrl) {
        return splitUrl.length == 1 || splitUrl[1] == null;
    }



    /**
     * This method will concat param with '&' from array on range startIndex to array.length - 1 inclusive.
     * Example - array - ["gender=female", "tag=123", "tag=1234", "tag=5678"]
     * if startIndex = 3, then result will be - tag=1234
     * if startIndex = 2, then result will be - tag=1234&tag=5678
     */
    private String getAsQueryParams(String[] splitParams, int startIndex) {
        return Arrays.stream(splitParams, startIndex, splitParams.length)
                .collect(Collectors.joining(Constants.DELIMITER_AMPERSAND));
    }


}
