package com.jzl.cloudTest.service.impl;


import com.jzl.cloudTest.mapper.PaymentMapper;
import com.jzl.cloudTest.pojo.Payment;
import com.jzl.cloudTest.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Resource
    private PaymentMapper paymentMapper;


    @Override
    public boolean addPayment(Payment payment) {
        return paymentMapper.createPayment(payment)>0;
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentMapper.selectPaymentById(id);
    }
}
