package com.qtrj.springcloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

/**
 * http://localhost:9001/hystrix
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashbordMain9001 {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashbordMain9001.class);
    }

}
