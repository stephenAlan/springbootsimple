package com.stephen.simple.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ssc on 2021-01-12 17:30 .
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> {

    private Integer code;

    private String msg;

    private T data;

    private Long timestamp;

    public static <T> Response success() {
        return Response.builder().code(ResponseCode.SUCCESS.getValue()).msg(ResponseCode.SUCCESS.getDesc()).data(1).timestamp(System.currentTimeMillis()).build();
    }

    public static <T> Response success(T data) {
        return Response.builder().code(ResponseCode.SUCCESS.getValue()).msg(ResponseCode.SUCCESS.getDesc()).data(data).timestamp(System.currentTimeMillis()).build();
    }

    public static <T> Response error() {
        return Response.builder().code(ResponseCode.ERROR.getValue()).msg(ResponseCode.ERROR.getDesc()).data(0).timestamp(System.currentTimeMillis()).build();
    }

    public static <T> Response error(T data) {
        return Response.builder().code(ResponseCode.ERROR.getValue()).msg(ResponseCode.ERROR.getDesc()).data(data).timestamp(System.currentTimeMillis()).build();
    }

}
