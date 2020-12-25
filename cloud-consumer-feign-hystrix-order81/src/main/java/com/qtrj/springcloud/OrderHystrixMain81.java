package com.qtrj.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderHystrixMain81 {

    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain81.class);
    }
}
