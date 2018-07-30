package com.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient  // springcloud 客户端
@EnableFeignClients    // feign 客户端特殊标识，默认集成ribbon。是一个http客户端
@EnableHystrix     // hystrix 断路器  可以和feign配合使用，则@EnableFeignClients @EnableHystrix2个注解都得加上
@EnableHystrixDashboard  // 仪表盘
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

