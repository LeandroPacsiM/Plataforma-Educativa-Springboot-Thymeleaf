package com.edu.plataforma.repository;

import com.edu.plataforma.model.Lesson;
import com.edu.plataforma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
