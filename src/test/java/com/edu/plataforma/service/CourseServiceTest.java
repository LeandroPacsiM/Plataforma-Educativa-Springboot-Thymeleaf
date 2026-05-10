package com.edu.plataforma.service;

import com.edu.plataforma.model.Course;
import com.edu.plataforma.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);
        course.setTitle("Java Basics");
        course.setDescription("Learn Java from scratch");
    }

    @Test
    void findAll_ShouldReturnList() {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));
        List<Course> result = courseService.findAll();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findById_ShouldReturnCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Optional<Course> result = courseService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Java Basics", result.get().getTitle());
    }

    @Test
    void save_ShouldReturnSavedCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        Course result = courseService.save(course);
        assertNotNull(result);
        assertEquals("Java Basics", result.getTitle());
    }

    @Test
    void update_ShouldReturnUpdatedCourse() {
        Course details = new Course();
        details.setTitle("Java Advanced");
        details.setDescription("Deeper Java concepts");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseService.update(1L, details);
        assertNotNull(result);
        assertEquals("Java Advanced", result.getTitle());
    }

    @Test
    void delete_ShouldReturnTrueWhenExists() {
        when(courseRepository.existsById(1L)).thenReturn(true);
        boolean result = courseService.delete(1L);
        assertTrue(result);
        verify(courseRepository, times(1)).deleteById(1L);
    }
}
