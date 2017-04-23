package com.jiangli.practice.eleme.dao;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/23 17:03
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable>
        extends PagingAndSortingRepository<T, ID> {

    List<T> listAll();
}
