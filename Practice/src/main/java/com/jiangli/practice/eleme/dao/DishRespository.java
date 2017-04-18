package com.jiangli.practice.eleme.dao;

import com.jiangli.practice.eleme.model.Dish;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/18 16:21
 */
//@Repository
public interface DishRespository extends CrudRepository<Dish, Integer> {
    List<Dish> findByMerchantId(Integer merchantId);
}
