package com.queen.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2022/12/18/10:28
 */
@Data
public class SetmealDto extends Setmeal {


    private List<SetmealDish> setmealDishes = new ArrayList<>();

    private String categoryName;
}
