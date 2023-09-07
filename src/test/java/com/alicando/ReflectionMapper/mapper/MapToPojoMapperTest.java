package com.alicando.ReflectionMapper.mapper;

import com.alicando.ReflectionMapper.example.Student;
import com.alicando.ReflectionMapper.exception.MappingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MapToPojoMapperTest {

    private Map<String,Object> mapToTransformWhole;

    private final MapToPojoMapper mapToPojoMapper;

    @Autowired
    public MapToPojoMapperTest(MapToPojoMapper mapToPojoMapper) {
        this.mapToPojoMapper = mapToPojoMapper;
    }

    @Test
    public void testSimpleMapping() throws MappingException {

        Student mappedStudent = mapToPojoMapper.map(mapToTransformWhole, Student.class);

        assertEquals("Roman",mappedStudent.getName());
        assertEquals("Alicando",mappedStudent.getLastName());
        assertEquals(25,mappedStudent.getAge());
        assertEquals(1000,mappedStudent.getListCourses().size());
    }

    @BeforeEach
    void setUp() {
        prepareWholeMap();
    }

    private void prepareWholeMap() {
        mapToTransformWhole = new HashMap<>();
        mapToTransformWhole.put("NAME","Roman");
        mapToTransformWhole.put("LAST_NAME","Alicando");
        mapToTransformWhole.put("AGE","25");

        List<Map<String,Object>> mapCourseList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            mapCourseList.add(prepareCourse("JUnit 5",10));
        }
        mapToTransformWhole.put("COURSES",mapCourseList);
    }

    private Map<String,Object> prepareCourse(String title, int hours) {
        Map<String,Object> course = new HashMap<>();
        course.put("COURSE_TITLE",title);
        course.put("COURSE_HOURS",Integer.toString(hours));
        return course;
    }
}