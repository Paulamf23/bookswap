package com.paula.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/general")
public class homeController {

    @GetMapping("")
    public String home() {
        return "general/home";
    }

    @GetMapping("/login") 
    public String loginPage() {
        return "general/login";
    }
}
