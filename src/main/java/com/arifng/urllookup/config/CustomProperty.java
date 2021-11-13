package com.arifng.urllookup.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Rana on 03/07/2021.
 */
@Configuration
@Getter
public class CustomProperty {
    @Value("${url.mapping.cahce.region}")
    private String cacheRegionName;
}
