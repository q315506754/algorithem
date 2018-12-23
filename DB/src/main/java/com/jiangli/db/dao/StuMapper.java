package com.jiangli.db.dao;

import com.jiangli.db.model.Student;

public interface StuMapper {
    public Student selectUser(Student user);
    public void insertUser(Student user);
    public void updateUser(Student user);
    public void deleteUser(String name);
}  