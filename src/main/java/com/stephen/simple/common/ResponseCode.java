package com.stephen.simple.common;

import lombok.Getter;

/**
 * Created by ssc on 2021-01-12 17:33 .
 * Description:
 */
@Getter
public enum ResponseCode {

    SUCCESS(200,"success"),
    ERROR(500,"error"),
    ;

    private int value;
    private String desc;

    ResponseCode(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
