package com.qtrj.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentHystrixFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Long id) {
        return "paymentInfo_OK，fallback，对方8001服务器已经当机了，😱:" + id;
    }

    @Override
    public String paymentInfo_Timeout() {
        return "paymentInfo_Timeout fallback😱:";
    }
}
