package com.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/11 14:18
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class SearchInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchInfoApplication.class, args);
    }
}
