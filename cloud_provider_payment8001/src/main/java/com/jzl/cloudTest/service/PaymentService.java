package com.jzl.cloudTest.service;

import com.jzl.cloudTest.pojo.Payment;

public interface PaymentService {

    boolean addPayment(Payment payment);

    Payment getPaymentById(Long id);
}
