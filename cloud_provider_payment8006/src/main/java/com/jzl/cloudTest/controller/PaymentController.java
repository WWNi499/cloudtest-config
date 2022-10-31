package com.jzl.cloudTest.controller;

import com.jzl.cloudTest.pojo.CommonResult;
import com.jzl.cloudTest.pojo.Payment;
import com.jzl.cloudTest.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {


    @Value("${server.port}")
    private String port;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/consul")
    public String paymentConsul(){
        return "springCloud with consul:"+port+"\t"+ UUID.randomUUID().toString();
    }


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

}
