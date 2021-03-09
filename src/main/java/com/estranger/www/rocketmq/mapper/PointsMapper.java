package com.estranger.www.rocketmq.mapper;

import com.estranger.www.rocketmq.bean.Points;
import org.apache.ibatis.annotations.Param;

public interface PointsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Points record);

    int insertSelective(Points record);

    Points selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Points record);

    int updateByPrimaryKey(Points record);

    Points selectRecordByOrderNo(@Param("orderNo") String orderNo);
}