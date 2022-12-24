package com.queen.service;

import com.queen.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜品及套餐分类 服务类
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
public interface CategoryService extends IService<Category> {
    /**
     * @description 自定义判断是否删除 菜品及套餐分类
     *
     * @author
     * @date 2022/12/7 21:53
     * @param id
     */
    public boolean remove(Long id);

}
