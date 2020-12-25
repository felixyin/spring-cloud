package com.qtrj.springcloud.service.impl;

import com.qtrj.springcloud.dao.PaymentDao;
import com.qtrj.springcloud.service.PaymentService;
import com.qtrj.springcloud.entities.Payment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
