package com.queen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.queen.domain.Dish;
import com.queen.domain.DishDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜品管理 服务类
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@Transactional
public interface DishService extends IService<Dish> {

    /**
     * @param dishDto 增加菜品表 以及口味表
     * @description 增加菜品表 以及口味表
     * 增加菜品表 以及口味表
     * @author 增加菜品表 以及口味表
     * @date 2022/12/13 19:43
     */
    @Transactional
    public boolean addDishWithDishFlavor(DishDto dishDto);


    public void removeWithFlavor(List<Long> ids);

    public void updateWithFlavor(DishDto dishDto);

}
