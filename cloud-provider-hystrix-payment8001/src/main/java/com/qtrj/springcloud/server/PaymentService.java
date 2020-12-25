package com.qtrj.springcloud.server;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    private String threadName = "";

    public String paymentInfo_OK(Long id) {
        return "线程池：" + threadName + " paymentInfo_OK,id:" + id + "\t哈哈～～";
    }

    public String paymentInfo_Timeout() {
        int tm = 3;
        try {
            TimeUnit.SECONDS.sleep(tm);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadName = Thread.currentThread().getName();
        return "线程池：" + threadName + " paymentInfo_Timeout," + "\t哈哈～～\t耗时" + tm + "秒";
    }

}
