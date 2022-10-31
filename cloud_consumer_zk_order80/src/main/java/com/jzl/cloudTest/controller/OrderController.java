package com.jzl.cloudTest.controller;


import com.jzl.cloudTest.pojo.CommonResult;
import com.jzl.cloudTest.pojo.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer")
public class OrderController {

    public static final String PAYMENT_URL = "http://cloud-payment-service-zk";

    @Autowired
    private RestTemplate restTemplate;//模拟rest风格请求


    @GetMapping("/payment")
    public CommonResult createPayment(Payment payment){
        //基本路径+传递的值+返回值
        return restTemplate.postForObject(PAYMENT_URL+"/payment",payment,CommonResult.class);
    }

    @GetMapping("/payment/{id}")
    public CommonResult getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/"+id,CommonResult.class);
    }


    @GetMapping("/payment/zk")
    public String paymentInfo() {
        String result = restTemplate.getForObject(PAYMENT_URL+"/payment/zk", String.class);
        System.out.println("消费者调用支付服务(zookeeper)--->result:" + result);
        return result;
    }


}
