package com.nautestech.www.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring")
public class CustomProperties{
    private Map<String, List<String>> customInfo = new HashMap<>();
    
    
}
