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

    @GetMapping("/login") // Agregamos un nuevo método para manejar la solicitud de la página de inicio de sesión
    public String loginPage() {
        return "general/login";
    }
}
