package com.stephen.simple;

import com.google.gson.Gson;
import com.stephen.simple.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * Created by ssc on 2020-11-13 0:30 .
 * Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class MockTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private JedisPool jedisPool;
    @Autowired
    private MockMvc mockMvc;

    /**
     * 点赞功能,并且3秒内不能重复操作
     * @throws Exception
     */
    @Test
    public void addTest() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/add")
                .param("userId", "10006")
        ).andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    /**
     * 获取前几名
     * @throws Exception
     */
    @Test
    public void topNTest() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/topn")
                .param("n", "3")
        ).andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    /**
     * 插入新的数据
     * @throws Exception
     */
    @Test
    public void updateTest() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/update")
                .param("userId", "1005")
                .param("score", "60")
        ).andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    /**
     * 获取用户排行
     * @throws Exception
     */
    @Test
    public void queryRankTest() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/queryRank")
                .param("userId", "1005")
        ).andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    /**
     * 获取用户排行
     * @throws Exception
     */
    @Test
    public void getAroundTest() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/getAround")
                .param("userId", "1005")
                .param("n","2")
        ).andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void helloTest() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                .param("name", "张三")
        )
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void bodyTest() throws Exception {
        User user = User.builder().name("张三").age(18).build();
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/body")
                .content(new Gson().toJson(user))
                .contentType(MediaType.APPLICATION_JSON)

        )
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void redisTest() {
        // redisTemplate.opsForValue().set("张三33","你好333！");
        redisTemplate.opsForZSet().add("张三",1,10);
        redisTemplate.opsForZSet().add("张三1",2,12);

        Double score = redisTemplate.opsForZSet().score("张三", 1);
        System.out.println("score: " + score);

        redisTemplate.opsForSet().add("李四",1,1,4,3,2,3);

        // 在集合的左边添加元素
        redisTemplate.opsForList().leftPush("list",1);
        // redisTemplate.opsForList().leftPush("list",2);
        // redisTemplate.opsForList().leftPush("list",0);
        // 获取指定下标的集合元素
        Object index2 = redisTemplate.opsForList().index("list", 2);
        System.out.println("size: " + redisTemplate.opsForList().size("list"));
        System.out.println(index2);
        // 在集合的指定位置插入元素,若指定位置已有元素,则覆盖;没有,则新增
        redisTemplate.opsForList().set("list",1,"update");
        // redisTemplate.opsForList().set("王五",2,"女");

        System.out.println("success");
    }

    @Test
    public void jedisPoolTest() {
        // try (Jedis jedis = jedisPool.getResource()){
        //     jedis.set("testpool","1");
        //     //
        //     jedis.set("key","1", SetParams.setParams().nx().px(1000));
        // }

    }


    // public static void main(String[] args) {
    //     AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
    //     String str1 = applicationContext.getBean("str1", String.class);
    //     System.out.println(str1);
    //
    // }

}
