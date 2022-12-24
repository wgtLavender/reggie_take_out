package com.queen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queen.dao.DishDao;
import com.queen.domain.Dish;
import com.queen.domain.DishDto;
import com.queen.domain.DishFlavor;
import com.queen.exception.CustomException;
import com.queen.service.DishFlavorService;
import com.queen.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜品管理 服务实现类
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishDao, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * @param dishDto
     * @description
     * @author
     * @date 2022/12/13 19:46
     */
    @Override
    public boolean addDishWithDishFlavor(DishDto dishDto) {

        //增加菜品
        boolean b = false;
        try {
            b = this.save(dishDto);
            List<DishFlavor> flavors = dishDto.getFlavors();
            List<DishFlavor> collect = flavors.stream().map((item) -> {
                item.setDishId(dishDto.getId());
                return item;
            }).collect(Collectors.toList());
            if (flavors.size() != 0 && flavors != null) {
                b = dishFlavorService.saveBatch(collect);
            }
        } catch (Exception e) {
            throw new CustomException("cccc");
        }

        return b;

    }

    @Override
    public void removeWithFlavor(List<Long> ids) {

        //删除菜品和菜品对应口味表
//删除菜品
        boolean b1 = this.removeByIds(ids);


        //删除菜品对应口味表
        ArrayList<Long> idLongs = new ArrayList<>();
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ids.size() != 0, DishFlavor::getDishId, ids);
        List<DishFlavor> list = dishFlavorService.list(wrapper);
        list.stream().forEach((item) -> {
            idLongs.add(item.getId());
        });
//        throw new CustomException("测试");
        boolean b = dishFlavorService.removeByIds(idLongs);

    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        // 根据id更新dish表
        this.updateById(dishDto);
        // 根据dish_id更新口味表
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        // wrapper.in
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishDto.setFlavors(flavors);
        boolean update = dishFlavorService.saveOrUpdateBatch(dishDto.getFlavors());
        System.out.println("------------------");


    }
}
