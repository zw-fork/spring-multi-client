package com.github.conf;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.util.AdminInterceptor;
import com.github.util.CurrentUserMethodArgumentResolver;
import com.github.util.MyRequestMappingHandlerMapping;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Resource private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

//    WebMvcRegistrationsAdapter a1 = new WebMvcRegistrationsAdapter();

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //自定义配置...
        FastJsonConfig config = new FastJsonConfig();
        /**
         * QuoteFieldNames———-输出key时是否使用双引号,默认为true
         WriteMapNullValue——–是否输出值为null的字段,默认为false
         WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
         WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
         WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
         WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
         */
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        converter.setFastJsonConfig(config);
        converters.add(converter);
    }

    // springboot 方式注入
//    通过 WebMvcRegistrations 来注入 RequestMappingHandlerMapping
    @Configuration
    public class WebMvcRegistrationsConfig implements WebMvcRegistrations {
        @Override
        public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
            return new MyRequestMappingHandlerMapping();
        }
    }


//        DispatcherServlet
//    HttpServletBean
//    ContextLoaderListener

    /**
     * 配置注入 spring-mvc 默认方式注入 requestMappingHandlerMapping
     */
//    @Configuration
//    public static class MyWebMvcConfigurationSupport extends WebMvcConfigurationSupport {
//        @Bean
//        @Override
//        public RequestMappingHandlerMapping requestMappingHandlerMapping(){
//            MyRequestMappingHandlerMapping handlerMapping = new MyRequestMappingHandlerMapping();
//            handlerMapping.setOrder(0);
//            handlerMapping.setInterceptors(getInterceptors());
//            handlerMapping.setContentNegotiationManager(mvcContentNegotiationManager());
//            handlerMapping.setCorsConfigurations(getCorsConfigurations());
//            PathMatchConfigurer configurer = getPathMatchConfigurer();
//            if (configurer.isUseSuffixPatternMatch() != null) {
//                handlerMapping.setUseSuffixPatternMatch(configurer.isUseSuffixPatternMatch());
//            }
//            if (configurer.isUseRegisteredSuffixPatternMatch() != null) {
//                handlerMapping.setUseRegisteredSuffixPatternMatch(configurer.isUseRegisteredSuffixPatternMatch());
//            }
//            if (configurer.isUseTrailingSlashMatch() != null) {
//                handlerMapping.setUseTrailingSlashMatch(configurer.isUseTrailingSlashMatch());
//            }
//            if (configurer.getPathMatcher() != null) {
//                handlerMapping.setPathMatcher(configurer.getPathMatcher());
//            }
//            if (configurer.getUrlPathHelper() != null) {
//                handlerMapping.setUrlPathHelper(configurer.getUrlPathHelper());
//            }
//            return handlerMapping;
//        }
//    }
}

