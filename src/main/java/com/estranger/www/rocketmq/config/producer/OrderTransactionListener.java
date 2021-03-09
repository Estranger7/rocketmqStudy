package com.estranger.www.rocketmq.config.producer;

import com.alibaba.fastjson.JSONObject;
import com.estranger.www.rocketmq.bean.Order;
import com.estranger.www.rocketmq.bean.TransactionLog;
import com.estranger.www.rocketmq.service.OrderService;
import com.estranger.www.rocketmq.service.TransactionLogService;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by：Estranger
 * Description：生产者监听类，负责执行本地事务和事务状态回查。
 * Date：2021/3/8 15:10
 */
@Component
public class OrderTransactionListener implements TransactionListener {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private OrderService orderService;

    private TransactionLogService transactionLogService;

    @Autowired
    public OrderTransactionListener(OrderService orderService,
                                    TransactionLogService transactionLogService) {
        this.orderService = orderService;
        this.transactionLogService = transactionLogService;
    }

    /**
     * 执行本地事务
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        logger.info("开始执行本地事务");
        LocalTransactionState state;
        try{
            //插入订单表
            //记录事务日志
            String body = new String(message.getBody());
            Order order = JSONObject.parseObject(body, Order.class);
            orderService.addOrderAndTransactionLog(order,message.getTransactionId());
            state = LocalTransactionState.COMMIT_MESSAGE;
            logger.info("本地事务执行成功{}",message.getTransactionId());
        }catch (Exception e){
            state = LocalTransactionState.ROLLBACK_MESSAGE;
            logger.info("本地事务执行失败");
        }
        return state;
    }

    /**
     * 事务状态回查
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        logger.info("开始执行消息回查{}" + messageExt.getTransactionId());
        LocalTransactionState state;
        TransactionLog transactionLog = transactionLogService.selectLogByTransactionId(messageExt.getTransactionId());
        if(transactionLog !=null){
            //能查到，说明本地事务执行成功，接着提交
            state = LocalTransactionState.COMMIT_MESSAGE;
        }else {
            state = LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return state;
    }
}
