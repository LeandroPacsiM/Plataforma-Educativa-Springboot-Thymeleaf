package com.edu.plataforma.service;

import com.edu.plataforma.model.Lesson;
import com.edu.plataforma.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> findById(Long id) {
        return lessonRepository.findById(id);
    }

    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson update(Long id, Lesson lessonDetails) {
        return lessonRepository.findById(id).map(lesson -> {
            lesson.setTitle(lessonDetails.getTitle());
            lesson.setContent(lessonDetails.getContent());
            lesson.setType(lessonDetails.getType());
            lesson.setCourse(lessonDetails.getCourse());
            return lessonRepository.save(lesson);
        }).orElse(null);
    }

    public boolean delete(Long id) {
        if (lessonRepository.existsById(id)) {
            lessonRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
