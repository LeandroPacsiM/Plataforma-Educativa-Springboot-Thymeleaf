package com.edu.plataforma.repository;

import com.edu.plataforma.model.Lesson;
import com.edu.plataforma.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
