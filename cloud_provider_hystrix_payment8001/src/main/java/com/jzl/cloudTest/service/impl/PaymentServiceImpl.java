package com.jzl.cloudTest.service.impl;


import cn.hutool.core.util.IdUtil;
import com.jzl.cloudTest.mapper.PaymentMapper;
import com.jzl.cloudTest.pojo.CommonResult;
import com.jzl.cloudTest.pojo.Payment;
import com.jzl.cloudTest.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

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

    @Override
    public String paymentInfo_Ok(Integer id) {
        return "线程池:"+Thread.currentThread().getName()+"paymentInfo_OK,id: "+id+"\t"+"O(∩_∩)O";
    }

    @Override
    // @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
    //         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    // })
    public String paymentInfo_TimeOut(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:"+Thread.currentThread().getName()+"paymentInfo_TimeOut,id: "+id+"\t"+"O(∩_∩)O，睡了5秒，真香";
    }

    // public String paymentInfo_TimeOutHandler(Integer id){
    //     String data = "/(ㄒoㄒ)/调用支付接口超时或异常：\t"+ "\t当前线程池" + Thread.currentThread().getName();
    //     // CommonResult<String> result = new CommonResult<>(4396,"hi",data);
    //     return data;
    // }

    //=========服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "3"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "10"),
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if(id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }
}
