package com.edu.plataforma.repository;

import com.edu.plataforma.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
