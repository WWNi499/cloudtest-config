package com.jzl.cloudTest;


import com.jzl.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @auther zzyy
 * @create 2020-01-28 16:18
 * 在启动该微服务的时候就能去加载我们的自定义Ribbon配置类，从而使配置生效，形如：
 */

@SpringBootApplication
@EnableFeignClients
public class MainFeignApp80 {

    public static void main(String[] args) {
        SpringApplication.run(MainFeignApp80.class);
    }
}
