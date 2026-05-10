package com.edu.plataforma.controller;

import com.edu.plataforma.model.Course;
import com.edu.plataforma.model.Lesson;
import com.edu.plataforma.model.Quiz;
import com.edu.plataforma.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class    CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/lessons")
    public ResponseEntity<List<Lesson>> getLessonsByCourse(@PathVariable Long id) {
        List<Lesson> lessons = courseService.getLessonsByCourse(id);
        return lessons != null ? ResponseEntity.ok(lessons) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/quizzes")
    public ResponseEntity<List<Quiz>> getQuizzesByCourse(@PathVariable Long id) {
        List<Quiz> quizzes = courseService.getQuizzesByCourse(id);
        return quizzes != null ? ResponseEntity.ok(quizzes) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.save(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        Course updatedCourse = courseService.update(id, courseDetails);
        return updatedCourse != null ? ResponseEntity.ok(updatedCourse) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        return courseService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}