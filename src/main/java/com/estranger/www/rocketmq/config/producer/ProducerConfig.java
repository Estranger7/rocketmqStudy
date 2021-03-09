package com.estranger.www.rocketmq.config.producer;

import com.estranger.www.rocketmq.bean.Order;
import com.google.gson.Gson;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by：Estranger
 * Description：生产者配置类
 * Date：2021/3/8 14:54
 */
@Configuration
public class ProducerConfig {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProducerConfig.class);

    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer setSendMsgTimeout;
    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.producer.producerGroup}")
    private String groupName;


    @Autowired
    private OrderTransactionListener orderTransactionListener;


    //执行任务的线程池
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 60,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(50));


    @Bean(destroyMethod = "shutdown")
    public TransactionMQProducer transactionMQProducer(){
        TransactionMQProducer producer = new TransactionMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setSendMsgTimeout(Integer.MAX_VALUE);
        producer.setExecutorService(executor);
        producer.setTransactionListener(orderTransactionListener);
        try {
            producer.start();
            LOGGER.info("producer is started. groupName:{}, namesrvAddr: {}", groupName, namesrvAddr);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }

}
