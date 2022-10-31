package com.jzl.cloudTest.service;

import com.jzl.cloudTest.pojo.CommonResult;
import com.jzl.cloudTest.pojo.Payment;
import com.jzl.cloudTest.service.impl.PaymentFallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-HYSTRIX-SERVICE",fallback = PaymentFallbackServiceImpl.class)
//controller使用service的方法feign调用方法上面的注解
//通过负载均衡调用到对应微服务端口
public interface PaymentFeignService {

    @GetMapping("/payment/{id}")
    CommonResult<Payment> getPayment(@PathVariable("id") Long id);

    @GetMapping("/payment/timeout")
    CommonResult<String> timeout();

    @GetMapping("/payment/hystrix/timeout/{id}")
    CommonResult<String> hystrixTimeout(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/ok/{id}")
    CommonResult<String> hystrixOk(@PathVariable("id") Integer id);


}
