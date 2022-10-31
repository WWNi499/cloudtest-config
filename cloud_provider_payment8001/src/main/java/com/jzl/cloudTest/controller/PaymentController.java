package com.jzl.cloudTest.controller;

import com.jzl.cloudTest.pojo.CommonResult;
import com.jzl.cloudTest.pojo.Payment;
import com.jzl.cloudTest.service.PaymentService;
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
}
