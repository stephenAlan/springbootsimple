package com.stephen.simple.service;

import com.stephen.simple.common.Response;

/**
 * Created by ssc on 2021-01-12 15:34 .
 * Description:
 */
public interface ScoreService {

    /**
     * 新增或累加积分
     * @param userId
     * @return
     */
    Response add(Long userId);

    /**
     * 获取前num名的排行榜数据
     * @param num
     * @return
     */
    Response findTopByNum(int num);

    /**
     * 插入用户数据
     * @param userId
     * @param score
     * @return
     */
    Response updateRank(Long userId,double score);

    /**
     * 获取用户的排行榜位置
     * @param userId
     * @return
     */
    Response getRankDo(Long userId);

    /**
     * 获取用户所在排行榜的位置,以及排行榜中前后n个用户的排行信息
     * @param userId
     * @param num
     * @return
     */
    Response getRankAroundUser(Long userId,int num);

}
