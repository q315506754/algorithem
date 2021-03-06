package com.jiangli.practice.eleme.dao;

import com.jiangli.practice.eleme.model.Merchant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/18 16:21
 */
public interface MerchantRepository extends BaseRepository<Merchant, Integer> {

    @Query("select u from Merchant u where u.name like %?1 order by u.likeit desc")
    List<Merchant> findByNameOrderByLikeitDesc(String name);

    @Query("select u from Merchant u order by u.likeit desc")
    List<Merchant> findAllOrderByLikeitDesc();

    @Transactional
    @Modifying
    @Query("update  Merchant u set u.likeit=?2 where u.id=?1")
    void setLikeit(Integer id,Integer likeit);
}
