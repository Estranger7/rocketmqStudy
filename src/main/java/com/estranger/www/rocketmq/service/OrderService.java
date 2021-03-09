package com.estranger.www.rocketmq.service;

import com.estranger.www.rocketmq.bean.Order; /**
 * Created by：Estranger
 * Description：
 * Date：2021/3/9 10:08
 */
public interface OrderService {
    void addOrderAndTransactionLog(Order order, String transactionId);
}
