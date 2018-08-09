package com.github.controller;

import com.github.model.CourseType;
import com.github.model.User;
import com.github.service.CourseTypeService;
import com.github.serviceRmtImpl.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
* Created by leolin on 7/9/2018.
*/
@RestController
@RequestMapping("pc")
public class HelleController {
    Logger logger = LoggerFactory.getLogger(HelleController.class);

    @Resource
    private MyService helloService;

    @Resource
    private CourseTypeService courseTypeService;

    @RequestMapping("deal")
    public void deal(){
        CourseType courseType = new CourseType();
        List<CourseType> list2 = courseTypeService.selectTypeByCondition(courseType,1,50);


        List<CourseType> list4 = courseTypeService.selectTypeByCondition(courseType,1,50);

//        List<CourseType> list3 = courseTypeService.upTypeByCondition(courseType,1,50);

    }

//    @Value("${server.port}")
//    private int serverPort = 0;
//
    @RequestMapping(value = "/hello/h", method = RequestMethod.GET)
    public String hello() {
        logger.error("------------------------");
        String response = helloService.sayHiFromClientOne("hello");
        return response;
    }


    @RequestMapping(value = "/hello/u", method = RequestMethod.GET)
    public User helloUser(String name) {
        logger.error("------------------------");
        User user = helloService.getUser(name);
        return user;
    }

}
