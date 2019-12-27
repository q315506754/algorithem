package com.jiangli.springmvc.test;

import com.jiangli.common.utils.ClassDescribeUtil;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.*;
import org.springframework.beans.propertyeditors.PropertiesEditor;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class SpringmvcTest {
    private long startTs;
    private Logger log = LoggerFactory.getLogger(SpringmvcTest.class);


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
    public void test_2_1() throws IOException {
        //Enumeration<URL> e =  ClassLoader.getSystemResources("META-INF/spring.schemas");
        Enumeration<URL> e =  Thread.currentThread().getContextClassLoader().getSystemResources("META-INF/spring.schemas");
        while (e.hasMoreElements()) {
            URL url = e.nextElement();
            System.out.println(url);
        }
    }

    @Test
    public void testCommit() {
        Map<String, Object> map = new HashMap<>();
        map.put("aaa", "bbb");
        map.put("bbb", 12341234);
        MutablePropertyValues m1 = new MutablePropertyValues(map);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("aaa", "bbb");
        map2.put("bbb", 33333);
        MutablePropertyValues m2 = new MutablePropertyValues(map2);

        log.debug(""+m1);

        PropertyValue aaa = m1.getPropertyValue("aaa");
        log.debug(""+aaa);

        log.debug(""+aaa.getName());
        log.debug(""+aaa.getValue());
        log.debug(""+ClassDescribeUtil.describe(aaa));

        MutablePropertyValues diff = (MutablePropertyValues)m1.changesSince(m2);
        log.debug(""+diff);
        log.debug(""+ClassDescribeUtil.describe(diff));

        diff.add("ccc", 123);
        diff.addPropertyValue("ddd", true);

        log.debug(""+diff);

        PropertyValue pv = new PropertyValue("dddd", "ddd2");
        diff.addPropertyValue(pv);

        log.debug(""+diff);

        pv.setAttribute("asd","sdfsdf");
        String[] strings = pv.attributeNames();
        log.debug(""+ Arrays.toString(strings));
        log.debug(""+ pv);

        Object source = pv.getSource();
        log.debug(""+ source);

        Object asd = pv.getAttribute("asd");
        log.debug(""+ asd);
        log.debug(""+ asd.getClass());
    }

    class  B{
        private String c;

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public B(String c) {
            this.c = c;
        }
    }
    class  A{
        private int a;
        private String b;
        private B c;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public B getC() {
            return c;
        }

        public void setC(B c) {
            this.c = c;
        }
    }
    @Test
    public void testCommit2() {
        A a = new A();
        BeanWrapperImpl impl = new BeanWrapperImpl(a);

        PropertyEditor customEditor = impl.findCustomEditor(int.class, null);
        System.out.println(customEditor);

        Class<Integer> x = int.class;
        System.out.println(x);
        System.out.println(x==Integer.class);
        System.out.println(ClassDescribeUtil.describe(x));//primitive=true
        System.out.println(ClassDescribeUtil.describe(Integer.class));// primitive=false
    }

    @Test
    public void testCommit3() {
        A a = new A();
        PropertyEditor editor = new PropertiesEditor();
        editor.setValue("asdsad");
        System.out.println(editor.getValue());
        System.out.println(editor.getValue().getClass());

        Map<String, Object> map = new HashMap<>();
        map.put("aaa", "bbb");
        map.put("bbb", 12341234);
        editor.setValue(map);
        Object value = editor.getValue();
        System.out.println(value);
        System.out.println(value.getClass());

        editor.setAsText("asdsad");
        System.out.println(editor.getValue());
        System.out.println(editor.getValue().getClass());
    }

    @Test
    public void testCommit4() {
        A a = new A();
        BeanWrapperImpl impl = new BeanWrapperImpl(a);
        Integer integer = impl.convertIfNecessary("1", Integer.class);
        System.out.println(integer);

        PropertyEditor customEditor = impl.findCustomEditor(Integer.class,null);
        System.out.println(customEditor);

        impl.setPropertyValue("b","asdasdasd");
        impl.setPropertyValue("a",334);
        impl.setPropertyValue("c",new B("adsdas"));
        System.out.println(impl.getPropertyValue("b"));
        System.out.println(impl.getPropertyValue("a"));
        System.out.println(impl.isReadableProperty("a"));
        System.out.println(impl.isWritableProperty("a"));
        System.out.println(impl.getPropertyValue("c.c"));
    }

    @Test
    public void testCommit5() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        A a = new A();
        a.a=12312;
        BeanWrapperImpl impl = new BeanWrapperImpl(a);


        Class<?> wrappedClass = impl.getWrappedClass();
        System.out.println(wrappedClass);
        Object wrappedInstance = impl.getWrappedInstance();
        System.out.println(wrappedInstance);

        PropertyDescriptor[] propertyDescriptors = impl.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            System.out.println(propertyDescriptor);
        }

        PropertyDescriptor a1 = new PropertyDescriptor("a", A.class);
        System.out.println(a1);

        System.out.println(ClassDescribeUtil.describe(a1));
        Method readMethod = a1.getReadMethod();
        Object invoke = readMethod.invoke(a);
        System.out.println(invoke);
        Object b = a1.getValue("a");
        System.out.println(b);

        System.out.println(impl.getAutoGrowCollectionLimit());


    }

    @Test
    public void test_asd() {
        A a = new A();
        a.a=12312;
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(a);
        System.out.println(beanWrapper.getClass());

        beanWrapper.registerCustomEditor(JSONObject.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
//                super.setAsText(text);
                setValue(JSONObject.fromObject(text));
            }

        });

        JSONObject jsonObject = beanWrapper.convertIfNecessary("{a:1}", JSONObject.class);
        System.out.println(jsonObject);
    }


}
