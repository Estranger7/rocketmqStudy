package com.estranger.www.rocketmq.config.producer;

import com.estranger.www.rocketmq.bean.Order;
import com.google.gson.Gson;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Created by：Estranger
 * Description
 * Date：2021/3/9 14:20
 */
@Component
public class ProducerProcess {

    private final Gson gson = new Gson();

    @Value("${rocketmq.producer.topic}")
    private String topic;

    @Autowired
    private TransactionMQProducer producer;

    public TransactionSendResult sendMsg(Order order){
        byte[] body = gson.toJson(order).getBytes(StandardCharsets.UTF_8);
        Message message = new Message(topic,body);
        try {
            return producer.sendMessageInTransaction(message,null);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return null;
    }

}
