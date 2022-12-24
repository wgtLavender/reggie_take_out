package com.queen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.queen.common.R;
import com.queen.domain.Category;
import com.queen.domain.Setmeal;
import com.queen.domain.SetmealDish;
import com.queen.domain.SetmealDto;
import com.queen.service.CategoryService;
import com.queen.service.SetmealDishService;
import com.queen.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 套餐 前端控制器
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;


    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {

        log.info("setmeal:{}", setmeal);
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        wrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        List<Setmeal> setmeals = setmealService.list(wrapper);
//        setmeals.stream().map((item) -> {
//            SetmealDto setmealDto = new SetmealDto();
//            BeanUtils.copyProperties(item,setmealDto);
//            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            dishFlavorLambdaQueryWrapper.eq(item.getCategoryId()!=null,dis)
//
//        })

        return R.success(setmeals);
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page();

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(name != null, Setmeal::getName, name);
        setmealService.page(setmealPage, setmealLambdaQueryWrapper);
        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        List<SetmealDto> setmealDtoList = setmealPage.getRecords().stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(item.getCategoryId() != null, Category::getId, item.getCategoryId());
            Category category = categoryService.getById(item.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            return setmealDto;

        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(setmealDtoList);
        return R.success(setmealDtoPage);

    }

    @PostMapping
    public R<String> setmeal(@RequestBody SetmealDto setmealDto) {
        log.info("setmeal： {}",setmealDto);

        setmealService.saveWithSetmealDish(setmealDto);
//        log.info("setmealDish： {}",setmealDish);

        return R.success("添加成功");
    }

    @GetMapping("/dish/{id}")
    public R<List<SetmealDish>> getDish(@PathVariable Long id) {

        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(id != null,SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(setmealDishLambdaQueryWrapper);

        return R.success(list);

    }
}

