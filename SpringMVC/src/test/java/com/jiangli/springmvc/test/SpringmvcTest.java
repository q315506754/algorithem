package com.jiangli.springmvc.test;

import com.jiangli.common.utils.ClassDescribeUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.propertyeditors.PropertiesEditor;

import java.beans.PropertyEditor;
import java.util.Arrays;
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

    class  A{
        private int a;
        private String b;

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
}
