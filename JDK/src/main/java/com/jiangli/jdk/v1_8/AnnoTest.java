package com.jiangli.jdk.v1_8;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 17:20
 */
public class AnnoTest {
    public static void main(String[] args) {
        MulAnno hint = Person.class.getAnnotation(MulAnno.class);
        System.out.println(hint);                   // null
        MulAnnos hints1 = Person.class.getAnnotation(MulAnnos.class);
        System.out.println(hints1.value().length);  // 3
        MulAnno[] hints2 = Person.class.getAnnotationsByType(MulAnno.class);
        System.out.println(hints2.length);          // 3
        //Runnable aNew = FactoryTest::new;
    }

}
