package com.jiangli.practice.eleme.dao;

import com.jiangli.practice.eleme.model.Dish;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/18 16:21
 */
//@Repository
public interface DishRepository extends BaseRepository<Dish, Integer> {
    @Query("select u from Dish u where u.merchantId = ?1 order by u.likeit desc,u.times desc")
    List<Dish> findByMerchantId(Integer merchantId);

    @Transactional
    @Modifying
    @Query("update  Dish u set u.likeit=?2 where u.id=?1")
    void setLikeit(Integer id,Integer likeit);

    @Transactional
    @Modifying
    @Query("update  Dish u set u.times=u.times+?2 where u.id=?1")
    void incTimes(Integer id,Integer inc);
}
