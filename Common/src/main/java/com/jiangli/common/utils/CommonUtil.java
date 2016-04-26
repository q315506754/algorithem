package com.jiangli.common.utils;

import com.jiangli.common.exception.ServiceException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiangli
 *         <p/>
 *         CreatedTime  2015/3/11 0011 16:23
 */
public class CommonUtil {
    /**
     * 获得Object的字符串
     *
     * @param obj
     *
     * @return String
     *
     * @author JiangLi CreateTime 2014-3-11 下午5:43:36
     */
    public static String nullToEmpty(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static <T> List<T> findAll(Class<T> cls) {
        return MongoDB.getMongoDB().findAll(cls);
    }

    public static String getDateDiff(Long d1, Long d2) {
        if (d1 != null && d2 != null) {
            long l = Math.abs(d1 - d2) / TimeUnit.DAYS.toMillis(1);
            return String.valueOf(l);
        }
        return "";
    }

    public static void throwExceptionIfNotObjectId(String str, String... strs) throws ServiceException {
        // throwExceptionIfNullString(str,strs);
        if (!isObjectId(str)) {
            throwException(strs);
        }
    }


    public static String getStringInMapThrowsExIfNull(Map map, String key, String exStr) throws ServiceException {
        Object obj = getObjectInMapThrowsExIfNull(map, key, exStr);
        String string = obj.toString();
        if (isStringNull(string)) {
            throwException(exStr);
        }
        return string;
    }

    public static Object getObjectInMapThrowsExIfNull(Map map, String key, String exStr) throws ServiceException {
        if (map == null) {
            throwException("map不能为空");
        }
        if (isStringNull(key)) {
            throwException("key不能为空");
        }
        Object object = map.get(key);
        if (object == null) {
            throwException(exStr);
        }
        return object;
    }

    public static void throwException(String... strs) throws ServiceException {
        if (strs == null || strs.length == 0) {
            throwException("throwError_0001", "参数对为空");
        }
        if (strs.length == 1) {
            throw new ServiceException(strs[0]);
        }
        if (strs.length % 2 != 0) {
            throwException("throwError_0002", "参数数目必须为偶数");
        }
        JSONObject obj = new JSONObject();
        for (int i = 0; i < strs.length; i += 2) {
            obj.put(strs[i], strs[i + 1]);
        }
        throw new ServiceException(obj);
    }

    public static String formatOrderPrice(Double d) {
        return formatOrderPrice(d, "");
    }

    public static String formatOrderPrice(Double d, String defaultWhenNull) {
        if (d == null) {
            return defaultWhenNull;
        }
        return new BigDecimal(d).setScale(0, BigDecimal.ROUND_FLOOR).toString();
    }

    public static boolean isStringNull(String obj) {
        return StringUtils.isEmpty(obj);
    }

    public static boolean isStringNotNull(String obj) {
        return !isStringNull(obj);
    }

    public static <T> T findOne(Class<T> cls, String prop, Object value) {
        return MongoDB.getMongoDB().findOne(Query.query(Criteria.where(prop).is(value)), cls);
    }

    public static <T> T findOneByObjectId(Class<T> cls, Object value) {
        if (value != null) {
            return MongoDB.getMongoDB().findOne(Query.query(Criteria.where("_id").is(value)), cls);
        } else {
            return null;
        }
    }

    public static <T> List<T> findList(Class<T> cls, String prop, Object value) {
        return MongoDB.getMongoDB().find(Query.query(Criteria.where(prop).is(value)), cls);
    }

    public static boolean isObjectId(String obj) {
        if (isStringNull(obj)) {
            return false;
        }
        return obj.length() == 24;
    }

    public static <T> T throwExceptionIfDataInMongoIsNull(Class<T> cls, String key,Object val, String exceptionStr) throws ServiceException {
        if (isStringNotNull(key) && val != null) {
            T one = findOne(cls, key, val);
            throwExceptionIfNull(one,exceptionStr);
            return one;
        }
        return null;
    }
    public static void throwExceptionIfNull(Object str, String... strs) throws ServiceException {
        if (str == null) {
            throwException(strs);
        }
    }
    public static void throwExceptionIfNotNull(Object str, String... strs) throws ServiceException {
        if (str != null) {
            throwException(strs);
        }
    }
}
