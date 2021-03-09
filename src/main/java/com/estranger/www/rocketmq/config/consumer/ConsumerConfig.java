package com.estranger.www.rocketmq.config.consumer;

import com.estranger.www.rocketmq.config.producer.OrderTransactionListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by：Estranger
 * Description：消费者配置类
 * Date：2021/3/8 14:54
 */
@Configuration
public class ConsumerConfig {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConsumerConfig.class);


    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private Integer consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private Integer consumeThreadMax;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private Integer consumeMessageBatchMaxSize;
    @Value("${rocketmq.consumer.consumerGroup}")
    private String consumerGroup;
    @Value("${rocketmq.producer.topic}")
    private String topic;


    @Autowired
    private OrderListener orderListener;

    private DefaultMQPushConsumer consumer;




    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe(topic,"*");
        consumer.registerMessageListener(orderListener);
        consumer.start();
    }


}
