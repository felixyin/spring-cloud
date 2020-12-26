package com.qtrj.springcloud.service;

import com.qtrj.springcloud.entities.Payment;

public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById( Long id);
}

