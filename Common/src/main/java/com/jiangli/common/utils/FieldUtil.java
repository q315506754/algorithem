package com.jiangli.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/12 0012 15:09
 */
public class FieldUtil {
    public static class FieldInfo{
        private Field field;
        private Object value;

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    public static interface FieldUpdateCallBack{
       void onUpdateEvent(FieldInfo fieldInfo);
    }

    public static void whenFieldUpdate(List<FieldInfo> fields, String fieldName,FieldUpdateCallBack callBack) {
        for (FieldInfo field : fields) {
            String name = field.getField().getName();
            if (name.equals(fieldName)) {
                callBack.onUpdateEvent(field);
            }
        }
    }

    public static List<FieldInfo> getUpdatedFields(Object old,Object neW) {
        List<FieldInfo> ret = new LinkedList<FieldInfo>();
        Field[] declaredFields = neW.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            try {
                String name = declaredField.getName();
                Object neWV = MethodUtil.invokeGetter(neW, name);
                Object oldV = MethodUtil.invokeGetter(old, name);

                boolean updated = false;
                updated = updated || neWV != null && !neWV.equals(oldV);
                updated = updated || neWV == null && oldV!=null;

                if (updated) {
                    FieldInfo fieldInfo = new FieldInfo();
                    fieldInfo.setField(declaredField);
                    fieldInfo.setValue(neWV);

                    ret.add(fieldInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return ret;
    }
}
