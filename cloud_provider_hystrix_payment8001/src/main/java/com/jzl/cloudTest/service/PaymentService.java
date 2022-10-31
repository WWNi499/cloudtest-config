package com.jzl.cloudTest.service;

import com.jzl.cloudTest.pojo.Payment;
import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {

    boolean addPayment(Payment payment);

    Payment getPaymentById(Long id);

    String paymentInfo_Ok(Integer id);

    String paymentInfo_TimeOut(Integer id);

    String paymentCircuitBreaker(Integer id);
}
