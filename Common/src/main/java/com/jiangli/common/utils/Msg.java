package com.jiangli.common.utils;


import com.jiangli.common.exception.MsgException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Jiangli
 *         <p/>
 *         CreatedTime  2015/3/11 0011 13:50
 */
public class Msg<T> implements Serializable {

    private Integer code = 0;
    private String msg = "成功";
    private String msgDetail = "";
    private T data;

    public Msg() {
    }

    public Msg(T data) {
        this.data = data;
    }

    public boolean isStringNull(String obj) {
        return StringUtils.isEmpty(obj);
    }

    public boolean isObjectId(String obj) {
        if (isStringNull(obj)) {
            return false;
        }
        return obj.length() == 24;
    }

    public static <T> T findOne(Class<T> cls, String prop, Object value) {
        return MongoDB.getMongoDB().findOne(Query.query(Criteria.where(prop).is(value)), cls);
    }

    public <T> T findOneByObjectId(Class<T> cls, Object value) {
        if (value != null) {
            return MongoDB.getMongoDB().findOne(Query.query(Criteria.where("_id").is(value)), cls);
        } else {
            return null;
        }
    }

    public <T> Object findOnePropertyByObjectStringId(Class<T> cls, String id, String propertyName, Object dft) {
        if (isObjectId(id)) {
            return findOnePropertyByObjectId(cls, new ObjectId(id), propertyName, dft);
        }
        return dft;
    }

    public <T> Object findOnePropertyByObjectId(Class<T> cls, ObjectId id, String propertyName, Object dft) {
        if (id != null) {
            T one = findOneByObjectId(cls, id);
            if (one != null) {
                try {
                    return BeanUtils.getProperty(one, propertyName);
                } catch (Exception e) {
                }
            }
        }

        return dft;
    }

    public boolean isStringNotNull(String obj) {
        return !isStringNull(obj);
    }

    public Msg<T> error(int codeP, String msgP) {
        return error(codeP, msgP, null);
    }

    public Msg<T> error(int codeP, String msgP, String msgDetailP) {
        setCode(codeP);
        if (msgP != null) {
            setMsg(msgP);

        }
        if (msgDetailP != null) {
            setMsgDetail(msgDetailP);

        }
        return this;
    }

    public List<String> getListByStringSplitByComma(String str) {
        List<String> ret = new LinkedList<String>();
        String[] split = str.split(",");
        for (String s : split) {
            ret.add(s.trim());
        }
        return ret;
    }

    public List<Integer> getIntListSplitByComma(String str) {
        List<String> listByStringSplitByComma = getListByStringSplitByComma(str);
        List<Integer> ret = new LinkedList<Integer>();
        for (String s : listByStringSplitByComma) {
            try {
                ret.add(Integer.parseInt(s));
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public String getStringFromList(List list) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                sb.append(String.valueOf(o));
                if (iterator.hasNext()) {
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }

    public List<ObjectId> getObjIdListSplitByComma(String str) {
        List<String> listByStringSplitByComma = getListByStringSplitByComma(str);
        List<ObjectId> ret = new LinkedList<ObjectId>();
        for (String s : listByStringSplitByComma) {
            try {
                ret.add(new ObjectId(s));
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public String getStringInMapThrowsExIfNull(Map map, String key, int code, String exStr) throws MsgException {
        Object obj = getObjectInMapThrowsExIfNull(map, key, code, exStr);
        String string = obj.toString();
        if (isStringNull(string)) {
            error(code, exStr).throwException();
        }
        return string;
    }

    public Long getLongInMapThrowsExIfNull(Map map, String key, int code, String exStr) throws MsgException {
        Object obj = getObjectInMapThrowsExIfNull(map, key, code, exStr);
        String string = obj.toString();
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            error(code, exStr).throwException();
        }
        return null;
    }

    public String nullToEmpty(Object obj) {
        return obj == null ? "" : obj.toString().trim();
    }

    public <T> T throwExceptionIfDataInMongoIsNull(Class<T> cls, String _id, int code1, String exceptionStr) throws MsgException {
        if (isStringNotNull(_id)) {
            T one = findOneByObjectId(cls, new ObjectId(_id));
            throwExceptionIfNull(one, code1, exceptionStr);
            return one;
        }
        return null;
    }

    public <T> T throwExceptionIfDataInMongoIsNull(Class<T> cls, String key, Object val, int code, String exceptionStr) throws MsgException {
        if (isStringNotNull(key) && val != null) {
            T one = findOne(cls, key, val);
            throwExceptionIfNull(one, code, exceptionStr);
            return one;
        }
        return null;
    }

    public void throwExceptionIfNull(Object str, int code, String msg) throws MsgException {
        if (str == null) {
            throwException(code, msg);
        }
    }

    public void throwExceptionIfNotNull(Object str, int code, String msg) throws MsgException {
        if (str != null) {
            throwException(code, msg);
        }
    }


    public Object getObjectInMapThrowsExIfNull(Map map, String key, int code, String exStr) throws MsgException {
        if (map == null) {
            throwException(-100, "map不能为空");
        }
        if (isStringNull(key)) {
            throwException(-100, "key不能为空");
        }
        Object object = map.get(key);
        if (object == null) {
            throwException(code, exStr);
        }
        return object;
    }

    public int getIntInMapDefault(Map map, String key, int dInt) {
        try {
            if (map.get(key) != null) {
                return Integer.parseInt(map.get(key).toString());
            }
        } catch (Exception e) {
        }
        return dInt;
    }

    public String getStringInMapDefault(Map map, String key, String dInt) {
        try {
            if (map.get(key) != null) {
                return map.get(key).toString();
            }
        } catch (Exception e) {
        }
        return dInt;
    }

    public Boolean getBooleanInMapDefault(Map map, String key, Boolean dInt) {
        try {
            if (map.get(key) != null) {
                return Boolean.parseBoolean(map.get(key).toString());
            }
        } catch (Exception e) {
        }
        return dInt;
    }

    public List<String> getListStringInMapDefault(Map map, String key, List<String> dInt) {
        try {
            if (map.get(key) != null) {
                Iterable iterable = (Iterable) map.get(key);
                List<String> ret = new LinkedList<String>();
                Iterator iterator = iterable.iterator();
                while (iterator.hasNext()) {
                    ret.add(String.valueOf(iterator.next()));
                }
                return ret;

            }
        } catch (Exception e) {
        }
        return dInt;
    }


    public void throwException(int codeP, String msgP) throws MsgException {
        throwException(codeP, msgP, null);
    }

    public void throwException(int codeP) throws MsgException {
        throwException(codeP, null, null);
    }

    public void throwException() throws MsgException {
        throw new MsgException();
    }

    public void throwException(int codeP, String msgP, String msgDetailP) throws MsgException {
        error(codeP, msgP, msgDetailP);
        throw new MsgException();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsgDetail() {
        return msgDetail;
    }

    public void setMsgDetail(String msgDetail) {
        this.msgDetail = msgDetail;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", msgDetail='" + msgDetail + '\'' +
                ", data=" + data +
                '}';
    }

    public Msg<T> throwIfNullException(Object obj, int codeP, String msgP) throws MsgException {
        if (obj == null) {
            throwException(codeP, msgP);
        }
        return this;
    }

    public void throwExceptionIfNotObjectId(String str, int code, String msg) throws MsgException {
        // throwExceptionIfNullString(str,strs);
        if (!isObjectId(str)) {
            throwException(code, msg);
        }
    }

    public Msg<T> throwIfStringNotObjectIdException(String obj, int codeP, String msgP) throws MsgException {
        if (!CommonUtil.isObjectId(obj)) {
            throwException(codeP, msgP);
        }
        return this;
    }
}
