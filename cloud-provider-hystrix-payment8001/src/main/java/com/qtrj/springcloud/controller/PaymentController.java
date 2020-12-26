package com.qtrj.springcloud.controller;

import com.qtrj.springcloud.server.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 压力测试工具：brew install jmeter
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("server.port")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Long id) {
        return paymentService.paymentInfo_OK(id);
    }

    @GetMapping("/payment/hystrix/timeout")
    public String paymentInfo_Timeout() {
        return paymentService.paymentInfo_Timeout();
    }

    @GetMapping("/payment/circuit/breaker/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Long id) throws Exception {
        log.info("***** id: " + id);
        return paymentService.paymentCircuitBreaker(id);
    }
}
