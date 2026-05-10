package com.edu.plataforma.data;

import com.edu.plataforma.repository.*;
import com.edu.plataforma.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class Dataloader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final QuizRepository quizRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            loadUserData();
            loadCourseData();
        }
    }

    private void loadUserData() {
        User admin = new User();
        admin.setEmail("admin@plataforma.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setName("Administrador");
        admin.setRole(Role.ADMIN);

        User student = new User();
        student.setEmail("estudiante@plataforma.com");
        student.setPassword(passwordEncoder.encode("user123"));
        student.setName("Estudiante de Prueba");
        student.setRole(Role.USER);

        userRepository.saveAll(Arrays.asList(admin, student));
        System.out.println("Usuarios cargados exitosamente.");
    }

    private void loadCourseData() {
        // Curso 1: Java
        Course javaCourse = new Course();
        javaCourse.setTitle("Fundamentos de Java");
        javaCourse.setDescription("Un curso completo para aprender las bases de Java desde cero.");
        javaCourse.setImageUrl("https://img.icons8.com/color/144/java-coffee-cup-logo.png");
        courseRepository.save(javaCourse);

        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Introducción a Java");
        lesson1.setContent("Java es un lenguaje orientado a objetos...");
        lesson1.setType("TEXT");
        lesson1.setCourse(javaCourse);

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Variables y Tipos de Datos");
        lesson2.setContent("https://www.youtube.com/embed/dQw4w9WgXcQ");
        lesson2.setType("VIDEO");
        lesson2.setCourse(javaCourse);

        lessonRepository.saveAll(Arrays.asList(lesson1, lesson2));

        Quiz quiz1 = new Quiz();
        quiz1.setQuestion("¿Qué es Java?");
        quiz1.setOptionA("Un lenguaje de programación");
        quiz1.setOptionB("Un tipo de café");
        quiz1.setOptionC("Un sistema operativo");
        quiz1.setCorrectAnswer("A");
        quiz1.setCourse(javaCourse);

        quizRepository.save(quiz1);

        // Curso 2: Spring Boot
        Course springCourse = new Course();
        springCourse.setTitle("Spring Boot Masterclass");
        springCourse.setDescription("Domina el desarrollo de microservicios con Spring Boot.");
        springCourse.setImageUrl("https://img.icons8.com/color/144/spring-logo.png");
        courseRepository.save(springCourse);

        Lesson sLesson1 = new Lesson();
        sLesson1.setTitle("Qué es Spring Boot");
        sLesson1.setContent("Spring Boot facilita la creación de aplicaciones...");
        sLesson1.setType("TEXT");
        sLesson1.setCourse(springCourse);

        lessonRepository.save(sLesson1);

        Quiz sQuiz1 = new Quiz();
        sQuiz1.setQuestion("¿Cuál es la ventaja principal de Spring Boot?");
        sQuiz1.setOptionA("Configuración automática");
        sQuiz1.setOptionB("Es más lento que Spring MVC");
        sQuiz1.setOptionC("Solo sirve para frontend");
        sQuiz1.setCorrectAnswer("A");
        sQuiz1.setCourse(springCourse);

        quizRepository.save(sQuiz1);

        System.out.println("Cursos, lecciones y quizzes cargados exitosamente.");
    }
}
