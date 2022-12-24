package com.queen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.queen.common.R;
import com.queen.common.util.BaseContext;
import com.queen.domain.Category;
import com.queen.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 菜品及套餐分类 前端控制器
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> add(@RequestBody Category category) {

        boolean save = categoryService.save(category);

        return save ? R.success("添加成功。"):R.error("添加失败");


    }





    @GetMapping("/page")
    public R pageCategory(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, String name) {
        Page<Category> pages = new Page<>(page, pageSize);

        System.out.println(name);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pages, queryWrapper);
        return R.success(pages);
    }


    @DeleteMapping
    public R deleteCategory(Long id) {
        boolean remove = categoryService.remove(id);

        return remove ? R.success("删除成功"):R.error("删除失败");

    }

    @PutMapping
    public R updateCategory(@RequestBody Category category, HttpServletRequest request) {
        log.info("线程id,{}",Thread.currentThread().getId());
        Long id = (Long) request.getSession().getAttribute("employee");
        BaseContext.setThreadLocal(id);
        boolean b = categoryService.updateById(category);

        return b ? R.success("修改成功。"):R.error("修改失败！");
    }

    @GetMapping("/list")
    public R getList(Category category) {
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(categoryLambdaQueryWrapper);
        return R.success(list);
    }
}

