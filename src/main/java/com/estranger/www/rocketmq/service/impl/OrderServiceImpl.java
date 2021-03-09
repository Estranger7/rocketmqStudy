package com.estranger.www.rocketmq.service.impl;

import com.estranger.www.rocketmq.bean.Order;
import com.estranger.www.rocketmq.bean.TransactionLog;
import com.estranger.www.rocketmq.mapper.OrderMapper;
import com.estranger.www.rocketmq.mapper.TransactionLogMapper;
import com.estranger.www.rocketmq.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by：Estranger
 * Description：
 * Date：2021/3/9 10:08
 */
@Service("OrderService")
public class OrderServiceImpl implements OrderService{

    private OrderMapper orderMapper;

    private TransactionLogMapper transactionLogMapper;


    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper,
                            TransactionLogMapper transactionLogMapper) {
        this.orderMapper = orderMapper;
        this.transactionLogMapper = transactionLogMapper;
    }

    @Transactional
    @Override
    public void addOrderAndTransactionLog(Order order, String transactionId) {
        orderMapper.insertSelective(order);
        TransactionLog log = new TransactionLog();
        log.setBusiness("order");
        log.setOrderId(order.getId());
        log.setId(transactionId);
        transactionLogMapper.insert(log);
    }

}
