package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/4 15:05
 */
@SpringBootApplication
@EnableEurekaClient
public class InfoInServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfoInServiceApplication.class, args);
    }
}
