package com.qtrj.springcloud.controller;

import com.qtrj.springcloud.entities.CommentResult;
import com.qtrj.springcloud.entities.Payment;
import com.qtrj.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommentResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("****插入结果：" + result);
        if (result > 0) {
            return new CommentResult<Integer>(200, "插入数据库成功：", serverPort, result);
        } else {
            return new CommentResult(444, "插入数据库失败", serverPort);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommentResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("****获取结果" + payment);
        if (null != payment) {
            return new CommentResult<Payment>(200, "查询成功：", serverPort, payment);
        } else {
            return new CommentResult<Long>(444, "没有对应的记录", serverPort, id);
        }
    }

    @GetMapping("/payment/discovery")
    public CommentResult<List<ServiceInstance>> discovery() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("\n" + instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return new CommentResult<>(200, "服务列表信息：", serverPort, instances);
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipKin() {
        return "hi，调用后，登陆zipKen查看调用链路";
    }

}
