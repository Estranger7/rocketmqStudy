package com.estranger.www.rocketmq.controller;

import com.estranger.www.rocketmq.bean.Order;
import com.estranger.www.rocketmq.service.OperateService;
import com.estranger.www.rocketmq.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by：Estranger
 * Description：
 * Date：2021/3/9 11:23
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private OperateService operateService;

    @Autowired
    public OrderController(OperateService operateService) {
        this.operateService = operateService;
    }

    @PostMapping("/createOrder")
    public void createOrder(@RequestBody Order order){
        logger.info("接收到订单数据：{}",order.toString());
        operateService.createOrder(order);
    }

}
