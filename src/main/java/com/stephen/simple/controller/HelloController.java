package com.stephen.simple.controller;

import com.stephen.simple.config.AppConfig;
import com.stephen.simple.entity.Person;
import com.stephen.simple.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by ssc on 2020-11-06 15:12 .
 * Description:
 */
@RestController
// @RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HelloController {

    // @Resource
    // private AppConfig appConfig;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Bean
    public String str1(){
        return "aa";
    }

    @Autowired
    private Person person;

    @Autowired
    private AppConfig appConfig;


    @GetMapping("/name")
    public String name() {
        return person.getName() + ":" + person.getAge();
    }

    @GetMapping("/getConfig")
    public String getConfig() {
        return appConfig.getUrl() +":"+ appConfig.getUsername() +":"+ appConfig.getPassword();
    }

    @RequestMapping()
    public Object hello() throws UnknownHostException {
        String ipv4 = Inet4Address.getLocalHost().getHostAddress();
        threadPoolExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return executor();
            }
        });
        return ipv4 + " hello dockerfile hello world";
    }

    public Integer executor() {
        int num = 10;
        System.out.println("executor  " + System.currentTimeMillis());
        while (num > 0) {
            insert();
            num = num - 1;
        }
        return 1;
    }

    public void insert() {
        System.out.println("insert  " + System.currentTimeMillis());
    }

    @RequestMapping("/hello")
    public Object hello1(@RequestParam String name, HttpServletRequest request/*,@RequestBody User user*/) {
        int age = Integer.valueOf(request.getParameter("age"));
        // return user.getName() + " hello dockerfile hello world " + user.getAge() + "\t" + age + "\t" + name;
        return name + " hello dockerfile hello world " + age ;
    }

    @RequestMapping("/body")
    public Object body(@Valid @RequestBody User user) {
        try {
            return user.getName() + "\t" + user.getAge() + " hello dockerfile hello world " ;
        } catch (Exception e) {

        }
        return null;
    }

    @Scheduled(cron = "0 54,55,56 * * * *")
    public void schedule() {
        System.out.println("hello " + new Date());
    }

}
