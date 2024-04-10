package com.paula.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/general")
public class homeController {
    
    @GetMapping("")  
    public String home(){
        return "general/home";
    }
}
