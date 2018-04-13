
package com.jiangli.springboot.mapper;

import com.jiangli.springboot.model.Recruit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 招生(班次)表
 */
@Repository
public interface RecruitMapper {
    
    /**
     * 招生(班次)表 新增
     */
    void save(Recruit recruit);
    
    /**
     * 招生(班次)表 删除
     */
    void delete(Recruit recruit);
    
    /**
     * 招生(班次)表 更新
     */
    void update(Recruit recruit);
    
    /**
     * 招生(班次)表 查询单个
     */
    Recruit get(long recruitId);
    
    /**
     * 招生(班次)表 查询列表
     */
    List<Recruit> listByCourseId(long courseId);

    List<Recruit> listNotFinished();

    List<Recruit> listAll();

    List<Recruit> listOfIds(@Param("recruitIds") List<Long> recruitIds);


}
