package com.jzl.cloudTest.controller;

import cn.hutool.core.util.IdUtil;
import com.jzl.cloudTest.pojo.CommonResult;
import com.jzl.cloudTest.pojo.Payment;
import com.jzl.cloudTest.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payment")
@Slf4j
@DefaultProperties(defaultFallback = "payment_global_fallbackMethod")
public class PaymentController {


    @Value("${server.port}")
    private String port;

    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping
    public CommonResult<Payment> addPayment(@RequestBody Payment payment){
        boolean flag = paymentService.addPayment(payment);
        CommonResult<Payment> result = new CommonResult<>();
        if (flag){
            result.setCode(200);
            result.setMessage("success"+port);
        }else {
            result.setCode(777);
            result.setMessage("error"+port);
        }
        return result;
    }

    @GetMapping("/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        CommonResult<Payment> result = new CommonResult<>();
        if (payment!=null){
            result.setData(payment);
            result.setCode(200);
            result.setMessage("success"+port);
        }else {
            result.setCode(666);
            result.setMessage("error"+port);
        }
        return result;
    }

    @GetMapping("/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println(service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            System.out.println(instance.getServiceId() + "\t" + instance.getHost()
                    + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("/MyLB")
    public CommonResult<String> getPort(){
        return new CommonResult<>(200,"success",port);
    }


    @GetMapping("/timeout")
    public CommonResult<String> timeout(){
        System.out.println("*****paymentFeignTimeOut from port: "+port);
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(3);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new CommonResult(200,"success",port);
    }

    @GetMapping("/hystrix/ok/{id}")
    public CommonResult<String> hystrixOk(@PathVariable("id") Integer id){
        String data= paymentService.paymentInfo_Ok(id);
        CommonResult<String> result = new CommonResult<>(200,"hi",data);
        return result;
    }
    @GetMapping("/hystrix/timeout/{id}")
    @HystrixCommand
    public CommonResult<String> hystrixTimeout(@PathVariable("id") Integer id){
        String data= paymentService.paymentInfo_TimeOut(id);
        log.info(data);
        CommonResult<String> result = new CommonResult<>(200,"hi",data);
        return result;
    }
    public CommonResult<String> payment_global_fallbackMethod() {
        String data = "Global异常处理信息，请稍后再试，/(ㄒoㄒ)/~~";
        CommonResult<String> result = new CommonResult<>(200, "hi", data);
        return result;
    }
    @GetMapping("/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("****result: "+result);
        return result;
    }



}
