package com.jiangli.common.utils;

import com.jiangli.common.model.Department;
import com.jiangli.common.model.Person;
import com.jiangli.common.model.Student;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/17 0017 15:53
 */
public class ModelUtil {
    public static List<Student> generateStudens(int size) {
        List<Student> ret = new ArrayList<>();
        List<Person> persons = generatePersons(size);
        ListIterator<Person> personListIterator = persons.listIterator();
        while (personListIterator.hasNext()) {
            int nextIndex = personListIterator.nextIndex();
            Person person = personListIterator.next();
            Student student = new Student();
            try {
                BeanUtils.populate(student, BeanUtils.describe(person));
                student.setDepartment( EnumUtil.getSpecialByOrdinal(Department.class, nextIndex));
                student.setGrade(nextIndex);
                ret.add(student);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static List<Person> generatePersons(int size) {
        List<Person> ret = new ArrayList<>();
        while (size-- > 0) {
            Person one = new Person();
            one.setName("我名叫张"+size);
            one.setState("状态" + size);
            one.setAge(size);
            ret.add(one);
        }
        return ret;
    }
}
