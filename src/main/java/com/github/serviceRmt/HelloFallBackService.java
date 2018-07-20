package com.github.serviceRmt;

import com.github.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by leolin on 7/11/2018.
 */
@Component
public class HelloFallBackService implements HelloService {
    @Override
    public String sayHiFromClientOne(@RequestParam(value = "name") String name) {
        return "sorry "+name;
    }

    @Override
    public User getUser(@RequestParam(value = "name") String name) {
        return null;
    }
}
