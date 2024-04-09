package com.paula.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/admin")
public class adminController {
    
    @GetMapping("")  
    public String home(){
        return "admin/home";
    }
}
