package com.queen.service.impl;

import com.queen.domain.Orders;
import com.queen.dao.OrdersDao;
import com.queen.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrdersService {

}
