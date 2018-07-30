package com.github.serviceRmtImpl;

//import com.github.serviceRmt.HelloService;
//import org.springframework.cloud.openfeign.FeignClient;

import com.github.serviceRmt.HelloService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * feignHttpClient 的实现
 * fallback 是实现本类的一个错误实现。
 * Created by leolin on 7/10/2018.
 */

@FeignClient(value = "SERVICE-HELLO" ,fallback = MyImplService.class)
public interface MyService extends HelloService{
//    @Override
//    String sayHiFromClientOne(@RequestParam("name") String s);
//
//    @Override
//    User getUser(@RequestParam("name") String s);
}
