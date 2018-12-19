package com.jiangli.db.dao;

import com.jiangli.db.model.PageBean;
import com.jiangli.db.model.User;
import com.jiangli.db.model.UserExtra;
import com.jiangli.db.model.UserLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-19 13:53
 */
@Repository
public interface UserMapper {

    void save(User user);

    void update(User user);

    User getByUserId(@Param("userId") Long userId);

    Long getByMobile(@Param("areaCode") String areaCode, @Param("mobile") String mobile);

    Long getByEmail(String email);

    /**
     * 逻辑删除用户
     * @param userId
     */
    void remove(long userId);

    /**
     * 禁用用户
     * @param userId
     */
    void disable(long userId);

    /**
     * 启用用户
     * @param userId
     */
    void enable(long userId);

    /**
     * 记录用户操作日志
     * @param log 日志对象，非空，其中userId/type/ctime字段非空，其它可选
     */
    void log(UserLog log);

    /**
     * 根据手机号批量查询用户信息
     * @param areaCode
     * @param mobiles
     * @return
     */
    List<User> listByMobiles(@Param("areaCode") String areaCode, @Param("mobiles") List<String> mobiles);

    /**
     * 根据邮箱批量查询用户信息
     * @param emails
     * @return
     */
    List<User> listByEmails(@Param("emails") List<String> emails);

    /**
     * 根据姓名模糊匹配教师总数
     * @param name
     * @return
     */
    int countTeacherByName(@Param("name") String name);

    /**
     * 根据姓名模糊匹配教师信息
     * @param name
     * @return
     */
    List<UserExtra> listTeacherByName(@Param("name") String name, @Param("page") PageBean page);

    /**
     * 根据userId集合查询教师数量
     * @param userIds
     * @return
     */
    int countTeacherByUserId(@Param("userIds") List<Long> userIds);

    /**
     * 根据userId批量查询教师信息
     * @param userIds
     * @return
     */
    List<UserExtra> listTeacherByUserId(@Param("userIds") List<Long> userIds, @Param("page") PageBean page);

    /**
     * 根据用户ID查询用户信息（批量，限制20条）
     * @param userIds
     * @return
     */
    List<User> list(@Param("userIds") List<Long> userIds);
}
