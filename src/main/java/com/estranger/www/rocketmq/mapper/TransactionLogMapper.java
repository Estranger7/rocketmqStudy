package com.estranger.www.rocketmq.mapper;

import com.estranger.www.rocketmq.bean.TransactionLog;

public interface TransactionLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(TransactionLog record);

    int insertSelective(TransactionLog record);

    TransactionLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TransactionLog record);

    int updateByPrimaryKey(TransactionLog record);
}