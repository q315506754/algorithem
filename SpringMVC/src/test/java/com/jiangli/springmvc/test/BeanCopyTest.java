package com.jiangli.springmvc.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class BeanCopyTest {
    private long startTs;
    private Logger log = LoggerFactory.getLogger(BeanCopyTest.class);


    @Before
    public void before() {
        startTs = System.currentTimeMillis();
        log.debug("----------before-----------");
    }

    @After
    public void after() {
        long cost = System.currentTimeMillis() - startTs;
        log.debug("----------after-----------:cost:" + cost + " ms");
    }



    @Test
    public void testCommit() {
    }

    public static  class  B{
        private Integer a;
        private Long b;
        private Boolean c;
        private D d;
        private Integer e;
        private Integer[] f;

        //getter&setter here

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public Long getB() {
            return b;
        }

        public void setB(Long b) {
            this.b = b;
        }

        public Boolean getC() {
            return c;
        }

        public void setC(Boolean c) {
            this.c = c;
        }


        public Integer getE() {
            return e;
        }

        public void setE(Integer e) {
            this.e = e;
        }

        public Integer[] getF() {
            return f;
        }

        public void setF(Integer[] f) {
            this.f = f;
        }

        @Override
        public String toString() {
            return "B{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    ", d=" + d +
                    ", e=" + e +
                    ", f=" + Arrays.toString(f) +
                    '}';
        }

        public D getD() {
            return d;
        }

        public void setD(D d) {
            this.d = d;
        }
    }
    public static class  D {
        private Integer aa;
        private Long bb;
        private Boolean cc;

        //getter&setter here

        public Integer getAa() {
            return aa;
        }

        public void setAa(Integer aa) {
            this.aa = aa;
        }

        public Long getBb() {
            return bb;
        }

        public void setBb(Long bb) {
            this.bb = bb;
        }

        public Boolean getCc() {
            return cc;
        }

        public void setCc(Boolean cc) {
            this.cc = cc;
        }

        @Override
        public String toString() {
            return "D{" +
                    "aa=" + aa +
                    ", bb=" + bb +
                    ", cc=" + cc +
                    '}';
        }
    }
    public static class  C {
        private String aa;
        private String bb;
        private String cc;

        //getter&setter here

        public String getAa() {
            return aa;
        }

        public void setAa(String aa) {
            this.aa = aa;
        }

        public String getBb() {
            return bb;
        }

        public void setBb(String bb) {
            this.bb = bb;
        }

        public String getCc() {
            return cc;
        }

        public void setCc(String cc) {
            this.cc = cc;
        }

        @Override
        public String toString() {
            return "C{" +
                    "aa='" + aa + '\'' +
                    ", bb='" + bb + '\'' +
                    ", cc='" + cc + '\'' +
                    '}';
        }
    }

    public static class  A{
        private String a;
        private String b;
        private String c;
        private C d;
        private int e;
        private int[] f;

        //getter&setter here

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public C getD() {
            return d;
        }

        public void setD(C d) {
            this.d = d;
        }

        public int getE() {
            return e;
        }

        public void setE(int e) {
            this.e = e;
        }

        public int[] getF() {
            return f;
        }

        public void setF(int[] f) {
            this.f = f;
        }

        @Override
        public String toString() {
            return "A{" +
                    "a='" + a + '\'' +
                    ", b='" + b + '\'' +
                    ", c='" + c + '\'' +
                    ", d=" + d +
                    ", e=" + e +
                    ", f=" + Arrays.toString(f) +
                    '}';
        }
    }

    //springcopy
    //conversion
    @Test
    public void testCommit2() {
        A orgA = new A();
        orgA.a="123";
        orgA.b="1234567";
        orgA.c="1";
        orgA.d = new C();
        orgA.d.aa="321";
        orgA.d.bb="7654321";
        orgA.d.cc="0";
        orgA.e=-99;
        orgA.f=new int[]{1,3,5,7,9};

        System.out.println(orgA);

        try {
            ConfigurableConversionService conversionService = new DefaultConversionService();

            //if you are not satisfied with the final A result, then
            //customize your own  Boolean->String Converter;
//            conversionService.addConverter(new Converter<Boolean, String>() {
//                @Override
//                public String convert(Boolean source) {
//                    return source==null||!source?"0":"1";
//                }
//            });

            //conversionService.addConverter(A.class, B.class, new ObjectConverter(conversionService, B.class));
            //conversionService.addConverter(B.class, A.class, new ObjectConverter(conversionService, A.class));
            //
            //conversionService.addConverter(C.class, D.class, new ObjectConverter(conversionService, D.class));
            //conversionService.addConverter(D.class, C.class, new ObjectConverter(conversionService, C.class));

            B b = conversionService.convert(orgA, B.class);
            System.out.println(b);

            A a = conversionService.convert(b, A.class);
            System.out.println(a);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static boolean isPrimitive(Class<?> cls) {
        return cls.isPrimitive() || cls == Boolean.class || cls == Byte.class
                || cls == Character.class || cls == Short.class || cls == Integer.class
                || cls == Long.class || cls == Float.class || cls == Double.class
                || cls == String.class || cls == Date.class || cls == Class.class;
    }

    class ObjectConverter implements  Converter<Object, Object> {
        private final ConversionService conversionService;
        private final Class targetClass;

        public ObjectConverter(ConversionService conversionService, Class targetClass) {
            this.conversionService = conversionService;
            this.targetClass = targetClass;
        }

        @Override
        public Object convert(Object source) {
            Object ret=null;
            try {
                ret = targetClass.newInstance();

                Field[] fields = targetClass.getDeclaredFields();
                for (Field field : fields) {
                    PropertyDescriptor sourceDescriptor = new PropertyDescriptor(field.getName(),source.getClass());
                    PropertyDescriptor targetDescriptor = new PropertyDescriptor(field.getName(),targetClass);

                    if (sourceDescriptor.getReadMethod()==null || targetDescriptor.getWriteMethod() == null) {
                        //record error here
                        break;
                    }

                    Class<?> sourcePType = sourceDescriptor.getPropertyType();
                    Class<?> targetPType = targetDescriptor.getPropertyType();
                    if(conversionService.canConvert(sourcePType,targetPType)){
                        Object sourceValue = sourceDescriptor.getReadMethod().invoke(source);
                        Object targetValue = conversionService.convert(sourceValue, targetPType);
                        targetDescriptor.getWriteMethod().invoke(ret,targetValue);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        }
    }

}
