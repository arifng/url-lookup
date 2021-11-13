package com.arifng.urllookup.config;

import com.arifng.urllookup.manager.DbToCacheUrlCopyManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Rana on 02/07/2021.
 */
@Configuration
public class CustomConfiguration {
    @Bean(initMethod = "start")
    public DbToCacheUrlCopyManager dbToCacheUrlCopyManager() {
        return new DbToCacheUrlCopyManager();
    }
}
