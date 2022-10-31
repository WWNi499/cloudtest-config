package com.jzl.cloudTest.mapper;

import com.jzl.cloudTest.pojo.Payment;
import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;

// @Repository
@Mapper
public interface PaymentMapper {

    /**
     * 新建交易
     * @param payment
     * @return
     */
    int createPayment(Payment payment);

    /**
     * 根据id查询交易
     * @param id
     * @return
     */
    Payment selectPaymentById(Long id);

}
