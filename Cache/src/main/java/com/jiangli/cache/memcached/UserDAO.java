package com.jiangli.cache.memcached;


import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.format.UseJson;

/**
* @author Jiangli
*
*         CreatedTime  2016/6/14 0014 10:40
*/
public interface UserDAO {
    @UseJson // 以json格式序列化
    @ReadThroughSingleCache(namespace = "star",expiration = 30)
    UserDO getUserById(@ParameterValueKeyProvider long id);

    int updateUserDO(UserDO userDO);
}
