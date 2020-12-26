package com.qtrj.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.qtrj.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "global_FallbackMethod", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
})
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String getPaymentById(@PathVariable("id") Long id) {
        return paymentService.paymentInfo_OK(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout")
   /*  // 自定义fallback配置
    @HystrixCommand(fallbackMethod = "paymentInfo_Timeout_handler", commandProperties = {
            // 设置此方法允许的默认超时时间
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2500")
    })*/
    @HystrixCommand
    public String paymentHystrixTimeout() {
//        openfeign (ribbon) 默认只等待1秒中
        return paymentService.paymentInfo_Timeout();
    }

    public String paymentInfo_Timeout_handler() {
        return "系统繁忙，运行报错了！consumer端" + "\t😯～～";
    }

    // 下面是全局fallback
    public String global_FallbackMethod() {
        return "全局报错配置，🦢";
    }

}
