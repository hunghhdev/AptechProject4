package com.aptech.project.hotel.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ConfigUtility {
    @Autowired
    private Environment env;

    public String getProperty(String propertyKey) {
        return env.getProperty(propertyKey);
    }
}
