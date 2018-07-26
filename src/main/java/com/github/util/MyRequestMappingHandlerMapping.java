package com.github.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 控制@Controller @RequestMapping 只扫描controller的类
 * Created by leolin on 7/23/2018.
 */
@Configuration
public class MyRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    @Override
    protected boolean isHandler(Class<?> beanType) {
        if(beanType.getName().endsWith("Controller")){
            return super.isHandler(beanType);
        }else{
            return false;
        }
    }
}
