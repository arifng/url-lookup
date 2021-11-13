package com.arifng.urllookup.controller;

import com.arifng.urllookup.factory.UrlLookupFactory;
import com.arifng.urllookup.form.UrlLookupForm;
import com.arifng.urllookup.data.UrlLookupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Rana on 02/07/2021.
 */
@RestController
@RequestMapping("/api/lookup")
public class UrlLookupController {
    private UrlLookupFactory urlLookupFactory;

    @Autowired
    public void setUrlLookupFactory(UrlLookupFactory urlLookupFactory) {
        this.urlLookupFactory = urlLookupFactory;
    }

    @GetMapping("/pretty-url")
    @ResponseBody
    public UrlLookupResponse getPrettyUrl(@Valid @RequestBody UrlLookupForm form) {
        return getMappedUrls(form, false);
    }

    @GetMapping("/param-url")
    @ResponseBody
    public UrlLookupResponse getParameterizedUrl(@Valid @RequestBody UrlLookupForm form) {
        return getMappedUrls(form, true);
    }

    private UrlLookupResponse getMappedUrls(UrlLookupForm form,
                                            boolean prettyInput) {
        List<String> results = urlLookupFactory.getLookupService(prettyInput)
                .getMappedUrls(form.getInputUrls());
        return new UrlLookupResponse(results);
    }
}
