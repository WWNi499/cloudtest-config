package com.jzl.cloudTest.service;

import com.jzl.cloudTest.pojo.CommonResult;
import com.jzl.cloudTest.pojo.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {

    @GetMapping("/payment/{id}")
    CommonResult<Payment> getPayment(@PathVariable("id") Long id);

    @GetMapping("/payment/timeout")
    CommonResult<String> timeout();
}
