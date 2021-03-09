package com.estranger.www.rocketmq.config.consumer;

import com.alibaba.fastjson.JSONObject;
import com.estranger.www.rocketmq.bean.Order;
import com.estranger.www.rocketmq.config.producer.ProducerConfig;
import com.estranger.www.rocketmq.service.PointsService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by：Estranger
 * Description：
 * Date：2021/3/9 16:43
 */
@Component
public class OrderListener implements MessageListenerConcurrently{

    public static final Logger LOGGER = LoggerFactory.getLogger(ProducerConfig.class);

    @Autowired
    private PointsService pointsService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        LOGGER.info("消费者线程监听到消息。");
        try{
            for (MessageExt message:list) {
                LOGGER.info("开始处理订单数据，准备增加积分....");
                Order order  = JSONObject.parseObject(message.getBody(), Order.class);
                pointsService.increasePoints(order);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }catch (Exception e){
            LOGGER.error("处理消费者数据发生异常。{}",e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
