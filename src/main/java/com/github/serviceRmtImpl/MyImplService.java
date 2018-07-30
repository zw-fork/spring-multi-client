package com.github.serviceRmtImpl;

import com.github.model.User;
import com.github.serviceRmt.HelloService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by leolin on 7/23/2018.
 */
@Component
public class MyImplService implements MyService {
    @Override
    public String sayHiFromClientOne(@RequestParam("name") String s) {
        return "sorry "+s;
    }

    @Override
    public User getUser(@RequestParam("name") String s) {
        return null;
    }
}
