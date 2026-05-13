package com.edu.plataforma.controller;

import com.edu.plataforma.model.Course;
import com.edu.plataforma.model.Lesson;
import com.edu.plataforma.model.Progress;
import com.edu.plataforma.model.Quiz;
import com.edu.plataforma.model.User;
import com.edu.plataforma.service.CourseService;
import com.edu.plataforma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "auth/landing";
    }

    @GetMapping("/home")
    public String home() {
        return "home/home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/courses/{id}")
    public String courseDetail(@PathVariable Long id, Model model, Authentication authentication) {
        Optional<Course> courseOptional = courseService.findById(id);

        if (courseOptional.isEmpty()) {
            model.addAttribute("course", null);
            model.addAttribute("courseId", id);
            model.addAttribute("lessons", Collections.emptyList());
            model.addAttribute("completedLessonIds", Collections.emptyList());
            model.addAttribute("totalLessons", 0);
            model.addAttribute("completedCount", 0);
            model.addAttribute("progressPercent", 0);
            model.addAttribute("enrolled", false);
            model.addAttribute("completedCourse", false);
            model.addAttribute("isAuthenticated", false);
            return "courses/detail";
        }

        Course course = courseOptional.get();
        List<Lesson> lessons = courseService.getLessonsByCourse(id);
        if (lessons == null) {
            lessons = Collections.emptyList();
        }
        List<Quiz> quizzes = courseService.getQuizzesByCourse(id);
        if (quizzes == null) {
            quizzes = Collections.emptyList();
        }

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName());
        List<Progress> userProgresses = Collections.emptyList();
        if (isAuthenticated) {
            userProgresses = userService.findByEmail(authentication.getName())
                    .map(User::getId)
                    .map(userService::getProgressByUser)
                    .orElse(Collections.emptyList());
            if (userProgresses == null) {
                userProgresses = Collections.emptyList();
            }
        }

        List<Long> completedLessonIds = new ArrayList<>();
        boolean enrolled = false;

        for (Progress progress : userProgresses) {
            Lesson lesson = progress.getLesson();
            if (lesson == null || lesson.getCourse() == null || !id.equals(lesson.getCourse().getId())) {
                continue;
            }

            enrolled = true;
            if (Boolean.TRUE.equals(progress.getCompleted()) && lesson.getId() != null && !completedLessonIds.contains(lesson.getId())) {
                completedLessonIds.add(lesson.getId());
            }
        }

        int totalLessons = lessons.size();
        int completedCount = completedLessonIds.size();
        int progressPercent = totalLessons == 0 ? 0 : (int) Math.round((completedCount * 100.0) / totalLessons);
        boolean completedCourse = totalLessons > 0 && completedCount >= totalLessons;
        Lesson selectedLesson = lessons.isEmpty() ? null : lessons.get(0);
        Lesson nextLesson = lessons.size() > 1 ? lessons.get(1) : null;

        model.addAttribute("course", course);
        model.addAttribute("courseId", id);
        model.addAttribute("lessons", lessons);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("selectedLesson", selectedLesson);
        model.addAttribute("nextLesson", nextLesson);
        model.addAttribute("completedLessonIds", completedLessonIds);
        model.addAttribute("totalLessons", totalLessons);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("progressPercent", progressPercent);
        model.addAttribute("enrolled", enrolled);
        model.addAttribute("completedCourse", completedCourse);
        model.addAttribute("isAuthenticated", isAuthenticated);

        return "courses/detail";
    }
}