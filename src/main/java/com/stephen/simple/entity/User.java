package com.stephen.simple.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ssc on 2021-01-11 9:59 .
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    // @Length(min = 5)
    private String name;
    private Integer age;

}
