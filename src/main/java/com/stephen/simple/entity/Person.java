package com.stephen.simple.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by stephen on 2021-03-31 14:44 .
 * Description:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private int age;

}
