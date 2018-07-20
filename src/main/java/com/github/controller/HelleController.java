package com.github.controller;

import com.github.model.User;
import com.github.serviceRmt.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * Created by leolin on 7/9/2018.
 */
@RestController
public class HelleController {
    Logger logger = LoggerFactory.getLogger(HelleController.class);

    @Resource
    private HelloService helloService;

//    @Value("${server.port}")
//    private int serverPort = 0;
//
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        logger.error("------------------------");
        String response = helloService.sayHiFromClientOne("hello");
        return response;
    }


    @RequestMapping(value = "/hello/user", method = RequestMethod.GET)
    public User helloUser(String name) {
        logger.error("------------------------");
        User user = helloService.getUser(name);
        return user;
    }

}
