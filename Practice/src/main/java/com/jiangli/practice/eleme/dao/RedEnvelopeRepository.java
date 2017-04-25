package com.jiangli.practice.eleme.dao;

import com.jiangli.practice.eleme.model.RedEnvelope;
import com.jiangli.practice.eleme.model.Rule;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/18 16:21
 */
public interface RedEnvelopeRepository extends BaseRepository<RedEnvelope, Integer> {
    @Query("select u from RedEnvelope u where u.isEnable=1 order by u.reach desc ,u.reduce desc")
    List<RedEnvelope> findList();
}
