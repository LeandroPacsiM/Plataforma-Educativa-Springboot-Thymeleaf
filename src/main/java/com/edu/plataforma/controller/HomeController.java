package com.edu.plataforma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

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
    public String courseDetail(@PathVariable Long id, Model model) {
        model.addAttribute("courseId",id);
        return "courses/detail";
    }
}