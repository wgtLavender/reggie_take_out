package com.queen.service.impl;

import com.queen.domain.OrderDetail;
import com.queen.dao.OrderDetailDao;
import com.queen.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailDao, OrderDetail> implements OrderDetailService {

}
