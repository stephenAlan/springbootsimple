package com.stephen.simple.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Created by ssc on 2021-01-11 9:59 .
 * Description:
 */
@Data
@Builder
public class User {

    private String name;
    private Integer age;

}
