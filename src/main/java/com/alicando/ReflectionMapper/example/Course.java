package com.alicando.ReflectionMapper.example;

import com.alicando.ReflectionMapper.annotation.ResultMapping;

public class Course {

    @ResultMapping(alias = "COURSE_TITLE")
    private String title;
    @ResultMapping(alias = "COURSE_HOURS")
    private int hours;

    public Course() {
    }

    public Course(String title, int hours) {
        this.title = title;
        this.hours = hours;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

}