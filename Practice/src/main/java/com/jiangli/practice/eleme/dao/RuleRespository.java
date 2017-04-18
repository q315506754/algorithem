package com.jiangli.practice.eleme.dao;

import com.jiangli.practice.eleme.model.Rule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/18 16:21
 */
public interface RuleRespository extends CrudRepository<Rule, Integer> {
   List<Rule> findByMerchantIdOrderBySortAsc(Integer merchantId);
}
