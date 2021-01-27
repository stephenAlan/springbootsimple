package com.stephen.simple.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Created by ssc on 2021-01-11 9:59 .
 * Description:
 */
@Data
@Builder
public class User {

    @Length(min = 5)
    private String name;
    private Integer age;

}
