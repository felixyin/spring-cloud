package com.qtrj.springcloud.controller;

import com.qtrj.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderHystrixController {

    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String getPaymentById(@PathVariable("id") Long id) {
        return paymentService.paymentInfo_OK(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout")
    public String paymentHystrixTimeout() {
//        openfeign (ribbon) 默认只等待1秒中
        return paymentService.paymentInfo_Timeout();
    }

}
