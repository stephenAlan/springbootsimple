package com.stephen.simple.controller;

import com.stephen.simple.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Created by ssc on 2020-11-06 15:12 .
 * Description:
 */
@RestController
public class HelloController {

    @Bean
    public String str1(){
        return "aa";
    }

    @RequestMapping()
    public Object hello() throws UnknownHostException {
        String ipv4 = Inet4Address.getLocalHost().getHostAddress();
        return ipv4 + " hello dockerfile hello world";
    }

    @RequestMapping("/hello")
    public Object hello1(@RequestParam String name) {
        return name + " hello dockerfile hello world " ;
    }

    @RequestMapping("/body")
    public Object body(@RequestBody User user) {
        return user.getName() + "\t" + user.getAge() + " hello dockerfile hello world " ;
    }

}
