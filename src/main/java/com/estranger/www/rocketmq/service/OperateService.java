package com.estranger.www.rocketmq.service;

import com.estranger.www.rocketmq.bean.Order;

/**
 * Created by：Estranger
 * Description：
 * Date：2021/3/9 14:10
 */
public interface OperateService {

    void createOrder(Order order);
}
