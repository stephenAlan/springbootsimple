package com.stephen.simple.controller;

import com.stephen.simple.common.Response;
import com.stephen.simple.service.ScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by ssc on 2021-01-12 16:07 .
 * Description:
 */
@RestController
public class ScoreController {

    @Resource
    private ScoreService scoreService;

    /**
     * 模拟点赞,有则累加,没有则新增
     * @param userId
     * @return
     */
    @PostMapping("add")
    public Response add(@RequestParam Long userId) {
        return scoreService.add(userId);
    }

    /**
     * 获取前n名的排行榜数据
     * @param num
     * @return
     */
    @GetMapping("/topN")
    public Response showTopN(@RequestParam int num) {
        return scoreService.findTopByNum(num);
    }

    /**
     * 插入或更新新的数据积分
     * @param userId
     * @param score
     * @return
     */
    @GetMapping("updateScore")
    public Response updateScore(@RequestParam Long userId,@RequestParam double score) {
        return scoreService.updateRank(userId,score);
    }

    /**
     * 获取用户的排行榜位置
     * @param userId
     * @return
     */
    @GetMapping("queryRank")
    public Response queryRank(@RequestParam Long userId) {
        return scoreService.getRankDo(userId);
    }

    /**
     * 获取用户所在排行榜位置及其前后n个用户的排行信息
     * @param userId
     * @param num
     * @return
     */
    @GetMapping("getAround")
    public Response getAround(@RequestParam Long userId,@RequestParam int num) {
        return scoreService.getRankAroundUser(userId,num);
    }

}
