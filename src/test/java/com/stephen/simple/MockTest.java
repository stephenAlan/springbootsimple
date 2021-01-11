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

    @Autowired
    private MockMvc mockMvc;

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

    // @Autowired
    // private JedisPool jedisPool;

    @Test
    public void redisTest() {
        redisTemplate.opsForValue().set("张三33","你好333！");
        // System.out.println(redisTemplate.opsForValue().get("张三22"));
        // try (Jedis jedis = jedisPool.getResource()){
        //
        //
        // }
    }



    // public static void main(String[] args) {
    //     AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
    //     String str1 = applicationContext.getBean("str1", String.class);
    //     System.out.println(str1);
    //
    // }

}
