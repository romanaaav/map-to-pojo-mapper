package com.alicando.ReflectionMapper.example;

import com.alicando.ReflectionMapper.annotation.ResultMapping;

import java.util.List;

public class Student {

    @ResultMapping(alias = "NAME")
    private String name;
    @ResultMapping(alias = "LAST_NAME")
    private String lastName;
    @ResultMapping(alias = "AGE")
    private Integer age;
    @ResultMapping(alias = "COURSES")
    private List<Course> listCourses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Course> getListCourses() {
        return listCourses;
    }

    public void setListCourses(List<Course> listCourses) {
        this.listCourses = listCourses;
    }
}
