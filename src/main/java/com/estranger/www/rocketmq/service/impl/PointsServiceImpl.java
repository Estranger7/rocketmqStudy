package com.estranger.www.rocketmq.service.impl;

import com.estranger.www.rocketmq.bean.Order;
import com.estranger.www.rocketmq.bean.Points;
import com.estranger.www.rocketmq.config.producer.ProducerConfig;
import com.estranger.www.rocketmq.mapper.OrderMapper;
import com.estranger.www.rocketmq.mapper.PointsMapper;
import com.estranger.www.rocketmq.service.PointsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by：Estranger
 * Description：积分类
 * Date：2021/3/9 16:47
 */
@Service("PointService")
public class PointsServiceImpl implements PointsService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PointsServiceImpl.class);

    @Autowired
    private PointsMapper pointsMapper;

    @Override
    public void increasePoints(Order order) {
        //先查询此笔订单是否已经增加了积分，以实现幂等性
        Points resultPoints = pointsMapper.selectRecordByOrderNo(order.getOrderNo());
        if(resultPoints != null){
            LOGGER.warn("此笔订单已经完成积分添加，请勿重复添加");
        }else {
            Points points = new Points();
            points.setOrderNo(order.getOrderNo());
            points.setRemarks("完成订单送积分");
            points.setUserId(order.getUserId());
            points.setPoints(10);
            pointsMapper.insertSelective(points);
            LOGGER.info("已为订单号{}增加积分：",points.getOrderNo());
        }
    }
}
