package com.queen.service;

import com.queen.domain.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.queen.domain.SetmealDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@Transactional
public interface SetmealService extends IService<Setmeal> {

    public void saveWithSetmealDish(SetmealDto setmealDto);

}
