package com.qtrj.springcloud.controller;

import com.qtrj.springcloud.entities.CommentResult;
import com.qtrj.springcloud.entities.Payment;
import com.qtrj.springcloud.lb.LoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    private static final String PAYMENT_URL = "http://CLOUD-PROVIDER-PAYMENT";

    @Resource
    private LoadBalance loadBalance;

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommentResult create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommentResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommentResult getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommentResult.class);
    }

    /**
     * spring-cloud-starter-netflix-eureka-client 已经引入ribbon负载均衡组件了
     *
     * @param payment
     * @return
     */
    @GetMapping("/consumer/payment/createEntity")
    public CommentResult createEntity(Payment payment) {
        ResponseEntity<CommentResult> responseEntity = restTemplate.postForEntity(PAYMENT_URL + "/payment/create", payment, CommentResult.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return new CommentResult(responseEntity.getStatusCodeValue(), "保存失败！", null);
        }
    }

    @GetMapping("/consumer/payment/getEntity/{id}")
    public CommentResult getPaymentEntry(@PathVariable("id") Long id) {
        ResponseEntity<CommentResult> forEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommentResult.class);
        boolean xxSuccessful = forEntity.getStatusCode().is2xxSuccessful();
        if (xxSuccessful) {
            return forEntity.getBody();
        } else {
            return new CommentResult(forEntity.getStatusCodeValue(), "查询失败！", null);
        }
    }

    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PROVIDER-PAYMENT");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance serviceInstance = loadBalance.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }

    @GetMapping("/consumer/payment/zipkin")
    public String zipkin() {
        //
        return restTemplate.getForObject("http://localhost:8001/payment/zipkin", String.class);
    }

}
