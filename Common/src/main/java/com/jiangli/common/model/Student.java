package com.jiangli.common.model;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/17 0017 15:51
 */
public class Student extends  Person{
    protected double grade;
    protected Department department;

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
