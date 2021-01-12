package com.stephen.simple.service;

import com.stephen.simple.common.Response;
import com.stephen.simple.entity.RankDO;
import com.stephen.simple.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by ssc on 2021-01-12 15:38 .
 * Description:
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    private static final String SCORE_KEY = "global_rank";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Response add(Long userId) {
        // 通过用户id和访问IP,处理重复请求
        String requestRedisKey = userId + IPUtils.getIpAddress();
        Object requestUserIdAndIp = redisTemplate.opsForValue().get(requestRedisKey);
        if (requestUserIdAndIp != null) {
            return Response.success("操作过于频繁,请稍后再试");
        }

        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        // 查询是否已存在
        Long rank = zSetOperations.rank(SCORE_KEY, String.valueOf(userId));
        if (rank == null) {
            zSetOperations.add(SCORE_KEY,String.valueOf(userId),1);
        } else {
            // 积分加1
            zSetOperations.incrementScore(SCORE_KEY, String.valueOf(userId), 1);
        }
        // 设置3秒的过期时间
        redisTemplate.opsForValue().set(requestRedisKey,1,3,TimeUnit.SECONDS);
        RankDO rankDO = new RankDO(userId, zSetOperations.score(SCORE_KEY, String.valueOf(userId)), zSetOperations.rank(SCORE_KEY, String.valueOf(userId)) + 1);
        return Response.success(rankDO);
    }

    @Override
    public Response findTopByNum(int num) {
        // 查询集合中指定顺序的值和score, 当num=0时表示获取集合全部内容
        Set<ZSetOperations.TypedTuple<String>> tupleSet = redisTemplate.opsForZSet().rangeWithScores(SCORE_KEY, 0, num - 1);
        List<RankDO> rankList = new ArrayList<>(tupleSet.size());
        long rank = 1;
        for (ZSetOperations.TypedTuple<String> sub : tupleSet) {
            rankList.add(new RankDO(Long.parseLong(sub.getValue()),Math.abs(sub.getScore()),rank++));
        }
        return Response.success(rankList);
    }

    @Override
    public Response updateRank(Long userId, double score) {
        //添加一个元素, zset与set最大的区别就是每个元素都有一个score，因此有个排序的辅助功能;  zadd
        redisTemplate.opsForZSet().add(SCORE_KEY, String.valueOf(userId), score);
        Long rank = redisTemplate.opsForZSet().rank(SCORE_KEY, String.valueOf(userId));
        return Response.success(new RankDO(userId,score,rank + 1));
    }

    @Override
    public Response getRankDo(Long userId) {
        return Response.success(getRankDoByUserId(userId));
    }

    public RankDO getRankDoByUserId(Long userId) {
        // 获取排行， 因为默认是0为开头，因此实际的排名需要+1
        Long rank = redisTemplate.opsForZSet().rank(SCORE_KEY, String.valueOf(userId));
        if (rank == null) {
            // 没有排行时，直接返回一个默认的
            return new RankDO(userId, 0D, -1L);
        }
        // 获取积分
        Double score = redisTemplate.opsForZSet().score(SCORE_KEY, String.valueOf(userId));
        return new RankDO(userId, Math.abs(score),rank + 1 );
    }

    @Override
    public Response getRankAroundUser(Long userId, int n) {
        // 首先是获取用户对应的排名
        RankDO rankDo = getRankDoByUserId(userId);
        if (rankDo == null || rankDo.getRank() <= 0) {
            // 用户没有上榜时，不返回
            return Response.success(Collections.emptyList());
        }

        // 因为实际的排名是从0开始的，所以查询周边排名时，需要将n-1
        // 查询集合中指定顺序的值和score，0, -1 表示获取全部的集合内容
        Set<ZSetOperations.TypedTuple<String>> result =
                redisTemplate.opsForZSet().rangeWithScores(SCORE_KEY, Math.max(0, rankDo.getRank() - n - 1), rankDo.getRank() + n - 1);
        List<RankDO> rankList = new ArrayList<>(result.size());
        long rank = rankDo.getRank() - n;
        for (ZSetOperations.TypedTuple<String> sub : result) {
            rankList.add(new RankDO(Long.parseLong(sub.getValue()), Math.abs(sub.getScore()),rank++));
        }
        return Response.success(rankList);
    }
}
