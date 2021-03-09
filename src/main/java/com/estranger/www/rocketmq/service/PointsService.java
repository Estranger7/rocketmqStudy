package com.estranger.www.rocketmq.service;

import com.estranger.www.rocketmq.bean.Order; /**
 * Created by：Estranger
 * Description：
 * Date：2021/3/9 16:47
 */
public interface PointsService {
    void increasePoints(Order order);
}
