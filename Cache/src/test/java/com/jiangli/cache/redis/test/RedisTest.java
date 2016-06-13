package com.jiangli.cache.redis.test;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;

import java.util.Arrays;
import java.util.Set;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/13 0013 15:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class RedisTest  {
    private static Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Value("${redis.maxTotal}")
    String maxTotal;

    @Autowired
    private JedisPool jedisPool;

    private Jedis jedis;

    @Before
    public void init() {
        jedis = jedisPool.getResource();
        String select = jedis.select(1);
        System.out.println(select);
        Assert.assertTrue(select.equals("OK"));
    }

    @Test
    public void func() {
        System.out.println(maxTotal);
        System.out.println(jedisPool);
        System.out.println(jedis);
    }

    @Test
    public void funcKey() throws Exception{
        String key = "aaa";
        String key_list = "key_list";
        String key_list_sorted = "key_list_sorted";
        String key_restored = "aaa_restored";
        String setRs = jedis.set(key, "bbbb");
        System.out.println(setRs);
        Assert.assertTrue(setRs.equals("OK"));

        String aaa = jedis.get(key);
        System.out.println(aaa);
        Assert.assertTrue(aaa.equals("bbbb"));

        byte[] dump = jedis.dump(key);
        System.out.println("dump:"+Arrays.toString(dump));

        Long delKey_restored = jedis.del(key_restored);
        System.out.println("delKey_restored:"+delKey_restored);
        String restoreRs = jedis.restore(key_restored, 0, dump);//ttl=0 ms -> no expire time
        System.out.println(restoreRs);
        String aaa_restored = jedis.get(key_restored);
        System.out.println(aaa_restored);
        Assert.assertTrue(aaa_restored.equals("bbbb"));

        //h?llo -> hello ， hallo 和 hxllo
        //h*llo -> hllo 和 heeeeello
        //h[ae]llo ->  hello 和 hallo ，但不匹配 hillo
        Set<String> keys = jedis.keys("*");
        System.out.println("keys:"+key);
        Assert.assertTrue(keys.contains(key));

//        字符串可以被编码为 raw (一般字符串)或 int (用字符串表示64位数字是为了节约空间)。
//        列表可以被编码为 ziplist 或 linkedlist 。 ziplist 是为节约大小较小的列表空间而作的特殊表示。
//        集合可以被编码为 intset 或者 hashtable 。 intset 是只储存数字的小集合的特殊表示。
//        哈希表可以编码为 zipmap 或者 hashtable 。 zipmap 是小哈希表的特殊表示。
//        有序集合可以被编码为 ziplist 或者 skiplist 格式。 ziplist 用于表示小的有序集合，而 skiplist 则用于表示任何大小的有序集合。
        String objectEncoding = jedis.objectEncoding(key);
        System.out.println("objectEncoding:"+objectEncoding);
        long objectRefcount = jedis.objectRefcount(key);
        System.out.println("objectRefcount:"+objectRefcount);
        long objectIdletime = jedis.objectIdletime(key);
        System.out.println("objectIdletime:"+objectIdletime);

        //expire key tts  (s)<-> PERSIST key
        //PEXPIRE  key tts  (milis)
        //PTTL key   (milis)
        //TTL key   (s)

        String randomKey = jedis.randomKey();
        System.out.println("randomKey:"+randomKey);

//        Long renamenx = jedis.renamenx(key, key);  //error

        jedis.del(key_list);
        Long lpush = jedis.lpush(key_list, "30", "1.5", "10", "8");
        System.out.println("lpush:"+lpush);

        SortingParams sortingParameters = new SortingParams();
        sortingParameters.desc();
        Long sortRs = jedis.sort(key_list, sortingParameters,"key_list_sorted");
        System.out.println(sortRs);


//        none (key不存在)
//        string (字符串)
//        list (列表)
//        set (集合)
//        zset (有序集)
//        hash (哈希表)
        String type_of_key = jedis.type(key);
        System.out.println("type_of_key:"+type_of_key);
        String type_of_key_list= jedis.type(key_list);
        System.out.println("type_of_key_list:" + type_of_key_list);
        Assert.assertTrue(type_of_key.equals("string"));
        Assert.assertTrue(type_of_key_list.equals("list"));


        ScanResult<String> scan = jedis.scan("0");
        System.out.println("scan:" + BeanUtils.describe(scan));

    }
}
