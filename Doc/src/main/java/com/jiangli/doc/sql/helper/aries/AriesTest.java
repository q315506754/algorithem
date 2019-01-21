package com.jiangli.doc.sql.helper.aries;

import java.lang.reflect.Field;

/**
 * @author Jiangli
 * @date 2019/1/10 14:18
 */
public class AriesTest {
    public static void main(String[] args) {
        //AriesMysqlKt.main(args);
        //ArisDB.INSTANCE.getDb_aries_course();

        Field[] declaredFields = ArisDB.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);

            declaredField.setAccessible(true);

            try {
                Object o = declaredField.get(new ArisDB());
                System.out.println(o);
                System.out.println(o instanceof Tbl);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

}
