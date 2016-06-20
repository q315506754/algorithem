package com.jiangli.datastructure.map;

import com.jiangli.common.model.Department;
import com.jiangli.common.model.Student;
import com.jiangli.common.utils.ModelUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/14 0014 16:39
 */
public class HashMapTest {

    public static void main(String[] args) {
        List<Student> students = ModelUtil.generateStudens(5);
//        System.out.println(students);

        students.stream().forEach((p)->System.out.println(p));
        printSplit();

        // Group employees by department
        Map<Department, List<Student>> byDept = students.stream()
                .collect(Collectors.groupingBy(Student::getDepartment));
        byDept.forEach((a, b) -> System.out.println(a + ":" + b));
        printSplit();

        // Partition students into passing and failing
        Map<Boolean, List<Student>> passingFailing = students.stream()
                .collect(Collectors.partitioningBy(s -> s.getGrade()>= 3));
        passingFailing.forEach((a, b) -> System.out.println(a + ":" + b));
        printSplit();

        // Compute sum of grade by department
        Map<Department, Double> totalByDept = students.stream()
                .collect(Collectors.groupingBy(Student::getDepartment,
                        Collectors.summingDouble(Student::getGrade)));
        totalByDept.forEach((a, b) -> System.out.println(a + ":" + b));
        printSplit();

    }

    public static void printSplit() {
        System.out.println("-----------------------------");
    }
}
