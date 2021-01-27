package com.stephen.simple.controller;

import com.stephen.simple.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Created by ssc on 2020-11-06 15:12 .
 * Description:
 */
@RestController
// @RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HelloController {

    // @Resource
    // private AppConfig appConfig;

    @Bean
    public String str1(){
        return "aa";
    }

    @RequestMapping()
    public Object hello() throws UnknownHostException {
        String ipv4 = Inet4Address.getLocalHost().getHostAddress();
        // System.out.println(appConfig.getUrl());
        // System.out.println(appConfig.getUsername());
        return ipv4 + " hello dockerfile hello world";
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

}
