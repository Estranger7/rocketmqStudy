package com.estranger.www.rocketmq.service.impl;

import com.estranger.www.rocketmq.bean.Order;
import com.estranger.www.rocketmq.config.producer.ProducerConfig;
import com.estranger.www.rocketmq.config.producer.ProducerProcess;
import com.estranger.www.rocketmq.service.OperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by：Estranger
 * Description：
 * Date：2021/3/9 14:10
 */
@Service("OperateService")
public class OperateServiceImpl implements OperateService{

    @Autowired
    private ProducerProcess producerProcess;

    @Override
    public void createOrder(Order order) {
        producerProcess.sendMsg(order);
    }
}
