package com.jiangli.common.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Jiangli
 * @date 2018/4/16 9:40
 */
public class BeanHelper {


    // 日期模式
    static String [] patterns = new String [] {"yyyy-MM-dd HH:mm:ss" ,"yyyy-MM-dd"} ;

    /**
     * <b style="color:#F00;">设定日期格式转换器，理论上下面注册机制是在单例上发生的，只需要执行一次，但实际情况在JBoss服务器下经常出现未注册自定义转换器的问题，怀疑实际执行过程中
     * BeanUtilsBean.getInstance()并非是真正单例的，将注册代码块做成初始化方法，在用到的工具类中执行，以避免上述问题</b>
     * @param bub 单例问题仍然存在，所以不再使用单例，来规避此问题，注册的对象由外部传入，工具方法也使用相同类来实现逻辑
     */
    private static final void init(final BeanUtilsBean bub) {
        // 注册自定义日期转换器
        ConvertUtilsBean cub = bub.getConvertUtils() ;
        BeanHelperDateConverter dateConverter = new BeanHelperDateConverter() ;
        dateConverter.setPatterns(patterns);
        cub.register(dateConverter, Date.class);
        cub.register(dateConverter, String.class);
    }

    /**
     * 对象间属性拷贝
     * @param origin	原对象
     * @param target	目标对象
     */
    public static final void copyProperties(Object origin ,Object target) {
        if(origin == null) return ;
        Assert.notNull(target);
        BeanUtils.copyProperties(origin, target);
    }

    /**
     * 对象类型转换(字段类型、名称应相同，字段数可以不等)
     * @param origin		原对象
     * @param targetType	目标类型
     * @return
     */
    public static final <T> T transTo(Object origin ,Class<T> targetType) {
        if(origin == null) return null ;
        Assert.notNull(targetType);
        T target = BeanUtils.instantiate(targetType) ;
        BeanHelper.copyProperties(origin, target);
        return target ;
    }

    /**
     * 集合类型转换，转换返回泛型List集合
     * @param colls
     * @param clazz
     * @return
     */
    public static final <T> List<T> transTo(Collection<?> colls , Class<T> clazz) {
        Assert.notNull(clazz ,"转换目标对象类型参数不能为空!");
        if(CollectionUtils.isEmpty(colls)) return null ;
        List<T> target = new ArrayList<T>() ;
        Iterator<?> iter = colls.iterator() ;
        while(iter.hasNext()) {
            T t = transTo(iter.next(), clazz) ;
            if(t != null) target.add(t) ;
        }
        return target ;
    }

    /**
     * MAP类型转换为Bean
     * @param map
     * @param type
     * @return
     */
    public static final <T> T transTo(Map<String ,?> map , Class<T> type) {
        T t = BeanUtils.instantiate(type) ;
        populate(t, map);
        return t ;
    }

    /**
     * 自定义类型转换器
     * @author	zhanglikun
     * @date	2016年4月19日 下午3:24:58
     * @param <S>	origin
     * @param <T>	target
     */
    public static interface Convertor<S ,T> {
        T convert(S origin) ;
    }

    /**
     * 使用自定义转换器转换列表，返回泛型List集合，转换过程中，过滤空值
     * @param colls
     * @param convertor
     * @return
     */
    public static final <S ,T> List<T> transTo(Collection<S> colls ,Convertor<S ,T> convertor) {
        Assert.notNull(convertor ,"转换器不能为空!");
        if(CollectionUtils.isEmpty(colls)) return null ;
        List<T> target = new ArrayList<T>() ;
        Iterator<S> iter = colls.iterator() ;
        while(iter.hasNext()) {
            T t = convertor.convert(iter.next()) ;
            if(t != null) target.add(t) ;
        }
        return target ;
    }

    /**
     * 使用Map填充Bean实体
     * @param bean
     * @param props
     */
    public static final void populate(Object bean ,Map<String ,?> props) {
        BeanUtilsBean bub = BeanUtilsBean.getInstance() ;
        try {
            init(bub) ;
            bub.populate(bean, props);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将Bean转换为Map<String ,String>，转换后会过滤空值(即Map结构中不包含空值)
     * @param origin
     * @return
     */
    public static final Map<String ,String> describe(Object origin) {
        BeanUtilsBean bub = BeanUtilsBean.getInstance() ;
        try {
            init(bub) ;
            Map<String ,String> data = bub.describe(origin) ;
            if(!CollectionUtils.isEmpty(data)) {
                Iterator<Map.Entry<String ,String>> iter = data.entrySet().iterator() ;
                while(iter.hasNext()) {
                    Map.Entry<String ,String> entry = iter.next() ;
                    if(entry.getValue() == null) {
                        iter.remove();	// 将空值过滤掉
                    } else if(StringUtils.equals(entry.getKey(), "class")) {
                        iter.remove();	// 过滤class属性
                    }
                }
            }
            return data ;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null ;
    }




}
