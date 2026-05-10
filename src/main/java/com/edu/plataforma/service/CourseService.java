package com.edu.plataforma.service;

import com.edu.plataforma.model.Course;
import com.edu.plataforma.model.Lesson;
import com.edu.plataforma.model.Quiz;
import com.edu.plataforma.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Lesson> getLessonsByCourse(Long id) {
        return courseRepository.findById(id).map(Course::getLessons).orElse(null);
    }

    public List<Quiz> getQuizzesByCourse(Long id) {
        return courseRepository.findById(id).map(Course::getQuizzes).orElse(null);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Course update(Long id, Course courseDetails) {
        return courseRepository.findById(id).map(course -> {
            course.setTitle(courseDetails.getTitle());
            course.setDescription(courseDetails.getDescription());
            course.setImageUrl(courseDetails.getImageUrl());
            return courseRepository.save(course);
        }).orElse(null);
    }

    public boolean delete(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
