package com.qtrj.springcloud.server;

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

    //    服务降级
    // 设置超时后的处理方法
    @HystrixCommand(fallbackMethod = "paymentInfo_Timeout_handler", commandProperties = {
            // 设置此方法允许的默认超时时间
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_Timeout() {
        int tm = 5;
        try {
            TimeUnit.SECONDS.sleep(tm);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadName = Thread.currentThread().getName();
        return "线程池：" + threadName + " paymentInfo_Timeout," + "\t哈哈～～\t耗时" + tm + "秒";
    }

    public String paymentInfo_Timeout_handler() {
        return "线程池：" + threadName + " paymentInfo_Timeout_handler," + "\t😯～～\t超时了";
    }

}
