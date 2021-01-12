package com.stephen.simple.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ssc on 2021-01-12 15:32 .
 * Description: 排名实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankDO {

    /**
     * 用户id，redis中存储
     * zset add(K key, V value, double score) 中的value
     */
    private Long userId;

    /**
     * 积分,redis中存储
     * zset add(K key, V value, double score) 中的score
     */
    private Double score;

    /**
     * 排名,代码中使用
     */
    private Long rank;

}
