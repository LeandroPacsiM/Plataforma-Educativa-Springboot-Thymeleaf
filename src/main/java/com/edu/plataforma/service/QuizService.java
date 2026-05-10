package com.edu.plataforma.service;

import com.edu.plataforma.model.Quiz;
import com.edu.plataforma.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz update(Long id, Quiz quizDetails) {
        return quizRepository.findById(id).map(quiz -> {
            quiz.setQuestion(quizDetails.getQuestion());
            quiz.setOptionA(quizDetails.getOptionA());
            quiz.setOptionB(quizDetails.getOptionB());
            quiz.setOptionC(quizDetails.getOptionC());
            quiz.setCorrectAnswer(quizDetails.getCorrectAnswer());
            quiz.setCourse(quizDetails.getCourse());
            return quizRepository.save(quiz);
        }).orElse(null);
    }

    public boolean delete(Long id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
