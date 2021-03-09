package com.estranger.www.rocketmq.service;

import com.estranger.www.rocketmq.bean.TransactionLog;

/**
 * Created by：Estranger
 * Description：
 * Date：2021/3/9 10:52
 */
public interface TransactionLogService {
    TransactionLog selectLogByTransactionId(String transactionId);
}
