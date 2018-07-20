package com.github.util;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * <p>自定义方法参数解析器</p>
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentUser.class) || parameter.getMethodAnnotation(CurrentUser.class) != null) {
            return true;
        }
        return false;
    }
    
    /** 解析参数, 返回参数值 */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

	    // cookie方式解析
	    CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
	    //从session的scope里取CurrentUser注解里的value属性值的key的value
	    Object user = webRequest.getAttribute(currentUserAnnotation.value(), NativeWebRequest.SCOPE_SESSION);
	    if (user == null) {
		    throw new IllegalStateException("CurrentUser Annotation doesn't get a value in session, check if user authenticated.");
	    }
        
        return user;
    }
}

