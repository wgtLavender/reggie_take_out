package com.queen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.queen.common.R;
import com.queen.domain.Category;
import com.queen.domain.Dish;
import com.queen.domain.DishDto;
import com.queen.domain.DishFlavor;
import com.queen.service.CategoryService;
import com.queen.service.DishFlavorService;
import com.queen.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜品管理 前端控制器
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {


    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @GetMapping("/page")
    public R pageDish(int page, int pageSize, String name) {
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishPage1 = new Page<>();
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.like(name != null, Dish::getName, name);
        dishLambdaQueryWrapper.orderByAsc(Dish::getPrice);
        dishService.page(dishPage, dishLambdaQueryWrapper);

        BeanUtils.copyProperties(dishPage, dishPage1, "records");

        List<Dish> records = dishPage.getRecords();
        log.info("records,{}", records.toString());
        List<DishDto> collect = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Category category = categoryService.getById(item.getCategoryId());
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());
        log.info("collect,{}", collect.toString());
        dishPage1.setRecords(collect);

        return R.success(dishPage1);
    }


    @PostMapping
    public R<String> saveDishWithFlavor(@RequestBody DishDto dishDto) {
        log.info("dishDto,{}", dishDto);
        boolean b = dishService.addDishWithDishFlavor(dishDto);
        return b ? R.success("添加成功") : R.error("添加失败");
    }

    @GetMapping("/list")
    public R<List<DishDto>> list(DishDto dishDto) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dishDto.getCategoryId() != null, Dish::getCategoryId, dishDto.getCategoryId());
        wrapper.eq(dishDto.getStatus() != null, Dish::getStatus, dishDto.getStatus());

        List<Dish> list = dishService.list(wrapper);

        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto1 = new DishDto();
            BeanUtils.copyProperties(item, dishDto1);
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(item.getId() != null, DishFlavor::getDishId, item.getId());
            List<DishFlavor> list1 = dishFlavorService.list(queryWrapper);
            dishDto1.setFlavors(list1);
            return dishDto1;
        }).collect(Collectors.toList());


        return R.success(dishDtoList);
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable String status, @RequestParam("ids") List<Long> ids) {

        log.info("status: {}", status);
        log.info("ids: {}", ids);
        ArrayList<Dish> dishes = new ArrayList<>();

        ids.stream().forEach(s -> {
            Dish dish = new Dish();
            dish.setStatus(Integer.valueOf(status));
            dish.setId(s);
            dishes.add(dish);
        });
        boolean b = dishService.updateBatchById(dishes);
        return b ? R.success("更新成功") : R.error("更新失败");

    }

    @DeleteMapping
    public R remove(@RequestParam List<Long> ids) {
        log.info("id:{}", ids);
        // 删除菜品和菜品口味表数据
        dishService.removeWithFlavor(ids);
        return R.success("删除成功");
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {

        log.info("id:{}", id);
        DishDto dishDto = new DishDto();
        // 获取dish对象
        Dish dish = dishService.getById(id);
        // 获取口味对象
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(id != null, DishFlavor::getDishId, id);

        List<DishFlavor> list = dishFlavorService.list(wrapper);
        BeanUtils.copyProperties(dish, dishDto);

        dishDto.setFlavors(list);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info("dishDto:{}",dishDto);
        dishService.updateWithFlavor(dishDto);
        return R.success("更新成功");
    }

}

