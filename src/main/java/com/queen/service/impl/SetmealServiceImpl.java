package com.queen.service.impl;

import com.queen.common.util.BaseContext;
import com.queen.domain.Setmeal;
import com.queen.dao.SetmealDao;
import com.queen.domain.SetmealDish;
import com.queen.domain.SetmealDto;
import com.queen.service.SetmealDishService;
import com.queen.service.SetmealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 套餐 服务实现类
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealDao, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;


    @Override
    public void saveWithSetmealDish(SetmealDto setmealDto) {
        Long id = BaseContext.getId();
        setmealDto.setCreateUser(id);
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        List<SetmealDish> collect = setmealDishes.stream().map((item) -> {
            item.setSetmealId(String.valueOf(setmealDto.getId()));
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(collect);
    }
}
