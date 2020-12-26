package com.qtrj.springcloud.server;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    private String threadName = "";

    public String paymentInfo_OK(Long id) {
        return "线程池：" + threadName + " paymentInfo_OK,id:" + id + "\t哈哈～～";
    }

    /**
     * 测试服务降级机制
     *
     * @return
     */
    // 设置超时或报错后的处理方法，只要是当前服务不可用，即可服务降级
    @HystrixCommand(fallbackMethod = "paymentInfo_Timeout_handler", commandProperties = {
            // 设置此方法允许的默认超时时间
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_Timeout() {
// 模拟报错情况
//        int age = 10 / 0;
// 模拟超时情况
//        int tm = 5;
        int tm = 2;
        try {
            TimeUnit.SECONDS.sleep(tm);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadName = Thread.currentThread().getName();
        return "线程池：" + threadName + " paymentInfo_Timeout," + "\t哈哈～～\t耗时" + tm + "秒\t👌😊";
    }

    public String paymentInfo_Timeout_handler() {
        return "线程池：" + threadName + " paymentInfo_Timeout_handler 系统繁忙，运行报错了！" + "\t😯～～";
    }


//    http://localhost:8001/payment/circuit/breaker/22
//    http://localhost:8001/payment/circuit/breaker/-33

    /**
     * 测试服务熔断机制
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),// 时间窗口期/时间范文
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(Long id) {
        if (id < 0) {
            throw new RuntimeException("id 不能是负数");
        }
        String uuid = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，业务流水号是：" + uuid;
    }

    public String paymentCircuitBreaker_fallback(Long id) {
        return "id: " + id + " 不能是负数，请等Hystrix熔断后，再试!!!";
    }


}
