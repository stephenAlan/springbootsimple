package com.stephen.simple.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:abc.properties")
@ConfigurationProperties(prefix = "jdbc")
public class AppConfig {
    private String url;
    private String username;
    private String password;
}