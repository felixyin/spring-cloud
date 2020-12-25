package com.qtrj.springcloud.controller;

import com.qtrj.springcloud.service.PaymentService;
import com.qtrj.springcloud.entities.CommentResult;
import com.qtrj.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping("/payment/create")
    public CommentResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("****插入结果：" + result);
        if (result > 0) {
            return new CommentResult<Integer>(200, "插入数据库成功", result);
        } else {
            return new CommentResult(444, "插入数据库失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommentResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("****获取结果" + payment);
        if (null != payment) {
            return new CommentResult<Payment>(200, "查询成功", payment);
        } else {
            return new CommentResult<Long>(444, "没有对应的记录", id);
        }
    }

}
