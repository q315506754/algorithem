package com.jiangli.db.dao;

import com.jiangli.db.dao.ext.MyProvider;
import com.jiangli.db.model.PageBean;
import com.jiangli.db.model.User;
import com.jiangli.db.model.UserExtra;
import com.jiangli.db.model.UserLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-19 13:53
 */
@Repository
public interface UserMapper {

    //需要自己解析传入的参数
    @SelectProvider(type = MyProvider.class, method = "dynamicSQL")
    List<User> testProvider(User user);

    //可以使用参数
    @Select("<script>select * from TBL_USER WHERE 1&lt;&gt; 0 AND IS_DELETED=#{isDeleted}</script>")
    List<User> testCustomSql(User user);

    void save(User user);

    void update(User user);

    User getByUserId(@Param("userId") Long userId);

    List<User> listAll(@Param("isDeleted") Integer isDeleted);
    List<User> listAllRowBounds(@Param("isDeleted") Integer isDeleted,RowBounds rowBounds);

    List<User> listMultiParameter(@Param("isDeleted") Integer isDeleted,@Param("p2") String p2,@Param("p3") String p3);


    //自动被插件处理 不需要在 xml 处理后两个参数
    //测试无效
    List<User> listByPageHelper(@Param("isDeleted") Integer isDeleted,@Param("pageNumKey") int pageNum, @Param("pageSizeKey") int pageSize);
    List<User> listByDto(QueryUserDto queryUserDto);
    List<User> listByUserDto(User user);

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
