package com.jzl.cloudTest.service.impl;

import com.jzl.cloudTest.pojo.CommonResult;
import com.jzl.cloudTest.pojo.Payment;
import com.jzl.cloudTest.service.PaymentFeignService;
import org.springframework.stereotype.Component;


@Component
public class PaymentFallbackServiceImpl implements PaymentFeignService {


    @Override
    public CommonResult<Payment> getPayment(Long id) {
        return new CommonResult<>(400,"o_0");
    }

    @Override
    public CommonResult<String> timeout() {
        return new CommonResult<>(400,"o_0");

    }

    @Override
    public CommonResult<String> hystrixTimeout(Integer id) {
        return new CommonResult<>(400,"o_0");
    }

    @Override
    public CommonResult<String> hystrixOk(Integer id) {
        return new CommonResult<>(400,"o_0");
    }
}
