package com.edu.plataforma.controller;

import com.edu.plataforma.model.Course;
import com.edu.plataforma.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);
        course.setTitle("Java Programming");
        course.setDescription("Master Java");
    }

    @Test
    void getAllCourses_ShouldReturnList() throws Exception {
        when(courseService.findAll()).thenReturn(Arrays.asList(course));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Java Programming"));
    }

    @Test
    void getCourseById_ShouldReturnCourse() throws Exception {
        when(courseService.findById(1L)).thenReturn(Optional.of(course));

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Programming"));
    }

    @Test
    void createCourse_ShouldReturnCreatedCourse() throws Exception {
        when(courseService.save(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Programming"));
    }

    @Test
    void deleteCourse_ShouldReturnNoContent() throws Exception {
        when(courseService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isNoContent());
    }
}
