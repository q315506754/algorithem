package com.jiangli.common.utils;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;

/**
 * Bean拷贝工具类 (用于domain和model实体转换用)
 *
 * @author zhaoxiang CreateDate 2013-9-11
 */
public class BeanCopyUtil {
    /**
     * 获取复制属性后的对象
     *
     * @param sourceBean
     * @param targetBean
     *
     * @return targetBean
     */
    public static <T> T getCopyBeanFromObjecIdToStr(Object sourceBean, T targetBean) {
        if (sourceBean != null && targetBean != null) {
            BeanCopier bc = BeanCopier.create(sourceBean.getClass(), targetBean.getClass(), true);
            StringConverter stringConverter = new StringConverter();
            bc.copy(sourceBean, targetBean, stringConverter);
            return targetBean;
        }
        return null;
    }

    public static <T, K> List<K> getCopyBeansFromObjecIdToStr(List<T> sourceBeans, Class<K> cls) {
        return getCopyBeansFromObjecIdToStr(sourceBeans, cls, null);
    }

    public static <T, K> List<K> getCopyBeansFromObjecIdToStr(List<T> sourceBeans, Class<K> cls, BeanCopyStrategy<T, K> strategy) {
        List<K> ret = new LinkedList<K>();
        for (T sourceBean : sourceBeans) {
            try {
                K k = cls.newInstance();
                getCopyBeanFromObjecIdToStr(sourceBean, k);

                if (strategy != null) {
                    strategy.copyStrategy(sourceBean, k);
                }

                ret.add(k);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return ret;
    }

    /**
     * id<String>domain实体复制到id<ObjectId> model实体
     *
     * @param sourceBean
     * @param targetBean
     *
     * @return targetBean
     */
    public static <T> T getCopyBeanFromStrToObjectId(Object sourceBean, T targetBean) {
        if (sourceBean != null && targetBean != null) {
            BeanCopier bc = BeanCopier.create(sourceBean.getClass(), targetBean.getClass(), true);
            ObjectIdConverter c = new ObjectIdConverter();
            bc.copy(sourceBean, targetBean, c);
            return targetBean;
        }
        return null;
    }

    /**
     * 获取复制属性后的对象
     *
     * @param sourceBean
     * @param targetBean
     *
     * @return targetBean
     */
    public static Object getCopyBean(Object sourceBean, Object targetBean) {
        BeanCopier bc = BeanCopier.create(sourceBean.getClass(), targetBean.getClass(), true);
        StringConverter stringConverter = new StringConverter();
        bc.copy(sourceBean, targetBean, stringConverter);
        return targetBean;
    }

    /**
     * id<String>domain实体复制到id<ObjectId> model实体
     *
     * @param sourceBean
     * @param targetBean
     *
     * @return targetBean
     */
    public static Object getCopyBeanForConvertObjectId(Object sourceBean, Object targetBean) {
        BeanCopier bc = BeanCopier.create(sourceBean.getClass(), targetBean.getClass(), true);
        ObjectIdConverter c = new ObjectIdConverter();
        bc.copy(sourceBean, targetBean, c);
        return targetBean;
    }

    private static class ObjectIdConverter implements Converter {
        @Override
        public Object convert(Object value, Class target, Object context) {
            if (value == null) {
                return null;
            }
            if (target.isAssignableFrom(ObjectId.class)) {
                String stringValue = value.toString();
                if (!CommonUtil.isObjectId(stringValue)) {
                    return null;
                }
                return new ObjectId(stringValue);
            } else {
                return value;
            }
        }
    }

    static class StringConverter implements Converter {
        @Override
        public Object convert(Object value, Class target, Object context) {
            if (value == null) {
                return null;
            }

            if (target.isAssignableFrom(String.class) && value instanceof ObjectId) {
                return value.toString();
            }
            return value;
        }
    }


}
