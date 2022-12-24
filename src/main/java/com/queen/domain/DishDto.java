package com.queen.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2022/12/13/19:41
 */
@Data
public class DishDto extends Dish{

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;


}
