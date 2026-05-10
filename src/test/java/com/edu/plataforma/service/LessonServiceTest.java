package com.edu.plataforma.service;

import com.edu.plataforma.model.Lesson;
import com.edu.plataforma.repository.LessonRepository;
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
public class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private LessonService lessonService;

    private Lesson lesson;

    @BeforeEach
    void setUp() {
        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitle("Lesson 1");
        lesson.setContent("Content for lesson 1");
        lesson.setType("TEXT");
    }

    @Test
    void findAll_ShouldReturnList() {
        when(lessonRepository.findAll()).thenReturn(Arrays.asList(lesson));
        List<Lesson> result = lessonService.findAll();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findById_ShouldReturnLesson() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        Optional<Lesson> result = lessonService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Lesson 1", result.get().getTitle());
    }

    @Test
    void save_ShouldReturnSavedLesson() {
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
        Lesson result = lessonService.save(lesson);
        assertNotNull(result);
        assertEquals("Lesson 1", result.getTitle());
    }

    @Test
    void update_ShouldReturnUpdatedLesson() {
        Lesson details = new Lesson();
        details.setTitle("Updated Lesson 1");

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        Lesson result = lessonService.update(1L, details);
        assertNotNull(result);
        assertEquals("Updated Lesson 1", result.getTitle());
    }

    @Test
    void delete_ShouldReturnTrueWhenExists() {
        when(lessonRepository.existsById(1L)).thenReturn(true);
        boolean result = lessonService.delete(1L);
        assertTrue(result);
        verify(lessonRepository, times(1)).deleteById(1L);
    }
}
