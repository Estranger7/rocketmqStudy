package com.estranger.www.rocketmq.service.impl;

import com.estranger.www.rocketmq.bean.TransactionLog;
import com.estranger.www.rocketmq.mapper.TransactionLogMapper;
import com.estranger.www.rocketmq.service.TransactionLogService;
import org.springframework.stereotype.Service;

/**
 * Created by：Estranger
 * Description：
 * Date：2021/3/9 10:52
 */
@Service("TransactionService")
public class TransactionLogServiceImpl implements TransactionLogService {

    private TransactionLogMapper transactionLogMapper;

    public TransactionLogServiceImpl(TransactionLogMapper transactionLogMapper) {
        this.transactionLogMapper = transactionLogMapper;
    }

    @Override
    public TransactionLog selectLogByTransactionId(String transactionId) {
        return transactionLogMapper.selectByPrimaryKey(transactionId);
    }
}
