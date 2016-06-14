package com.jiangli.cache.memcached;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.format.UseJson;
import org.springframework.stereotype.Repository;

/**
* Created by IntelliJ IDEA.
* User: huxing(huxing1985#gmail.com)
* Date: 12-5-18
* Time: 下午5:50
*/
@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
    @Override
    @UseJson // 以json格式序列化
    @ReadThroughSingleCache(namespace = "star",expiration = 30)
    public UserDO getUserById(@ParameterValueKeyProvider long id) {
        UserDO userDO = new UserDO();
        userDO.setId(1024l);
        userDO.setName("Inzaghi");
        userDO.setPassword("password");
        return userDO;
    }

    @Override
    public int updateUserDO(UserDO userDO) {
        return 0;
    }

    @InvalidateSingleCache(namespace = "star")
    public int deleteUser(@ParameterValueKeyProvider long userId) {
        return 0;
    }

}