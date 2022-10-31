package com.jzl.cloudTest.controller;


import com.jzl.cloudTest.config.lb.LoadBalancer;
import com.jzl.cloudTest.pojo.CommonResult;
import com.jzl.cloudTest.pojo.Payment;
import com.jzl.cloudTest.service.PaymentFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/consumer")
public class OrderController {

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    private RestTemplate restTemplate;//模拟rest风格请求

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/payment")
    public CommonResult createPayment(Payment payment){
        //基本路径+传递的值+返回值
        return restTemplate.postForObject(PAYMENT_URL+"/payment",payment,CommonResult.class);
    }

    @GetMapping("/payment/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return paymentFeignService.getPayment(id);
    }

    @GetMapping("/payment/timeout")
    public CommonResult<String> timeout(){
        return paymentFeignService.timeout();
    }

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private LoadBalancer loadBalancer;



    @GetMapping("/payment/MyLB")
    public CommonResult getPaymentPort(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size()<=0){
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instance(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri+"/payment/MyLB",CommonResult.class);
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    // @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod" +
    //         "",commandProperties = {
    //         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1000")
    // })
    // @HystrixCommand(fallbackMethod = "paymentInfoHandlerException")
    public CommonResult<String> hystrixTimeout(@PathVariable("id") Integer id){
        return paymentFeignService.hystrixTimeout(id);
    };

    // public CommonResult<String> paymentTimeOutFallbackMethod(@PathVariable("id") Integer id)
    // {
    //     String data = "我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    //     CommonResult<String> result = new CommonResult<>(4396, "error", data);
    //     return result;
    // }
    // public CommonResult<String> paymentInfoHandlerException(Integer id){
    //     String data = "我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    //     CommonResult<String> result = new CommonResult<>(4396, "error", data);
    //     return result;
    // }


    @GetMapping("/payment/hystrix/ok/{id}")
    public CommonResult<String> hystrixOk(@PathVariable("id") Integer id){
        return paymentFeignService.hystrixOk(id);
    }


}
