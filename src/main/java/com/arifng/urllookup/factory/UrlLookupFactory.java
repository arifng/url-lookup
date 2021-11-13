package com.arifng.urllookup.factory;

import com.arifng.urllookup.service.business.UrlLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Rana on 03/07/2021.
 */
@Component
public class UrlLookupFactory {
    private UrlLookupService parameterizedUrlLookupService;
    private UrlLookupService prettyUrlLookupService;

    @Autowired
    @Qualifier("parameterizedUrlLookupService")
    public void setParameterizedUrlLookupService(UrlLookupService parameterizedUrlLookupService) {
        this.parameterizedUrlLookupService = parameterizedUrlLookupService;
    }

    @Autowired
    @Qualifier("prettyUrlLookupService")
    public void setPrettyUrlLookupService(UrlLookupService prettyUrlLookupService) {
        this.prettyUrlLookupService = prettyUrlLookupService;
    }

    public UrlLookupService getLookupService(boolean prettyInput) {
        return prettyInput ? parameterizedUrlLookupService : prettyUrlLookupService;
    }
}
