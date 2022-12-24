package com.queen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.queen.domain.Category;
import com.queen.dao.CategoryDao;
import com.queen.domain.Dish;
import com.queen.domain.Setmeal;
import com.queen.exception.CustomException;
import com.queen.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queen.service.DishService;
import com.queen.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;


    @Override
    public boolean remove(Long id) {

        // 菜品及套餐分类 此分类下是否有菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if (count1 > 0) {
            // 抛出自定义异常

            throw new CustomException("此分类下是否有菜品,不能删除！");
        }


        // 菜品及套餐分类 此分类下是否有套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            // 抛出自定义异常
            throw new CustomException("此分类下是否有套餐,不能删除！");
        }


        // 都没有即可删除

        boolean b = super.removeById(id);
        return b;
    }
}
