package com.jiangli.common.utils;

import com.jiangli.common.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/17 0017 15:53
 */
public class ModelUtil {
    public static List<Student> generateStudens(int size) {
        List<Student> ret = new ArrayList<>();
        while (size-- > 0) {
            Student one = new Student();

            ret.add(one);
        }
        return ret;
    }
}
