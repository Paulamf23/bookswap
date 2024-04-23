package com.paula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paula.model.User;
import com.paula.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/general")
public class AppController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passEncoder;

    @GetMapping("")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    @GetMapping("/addNewBook")
    public String addNewBook() {
        return "addNewBook";
    }

    @PostMapping("/proceso_register")
    public String registar(User user, @RequestParam("repeatPassword") String repeatPassword, Model model) {
        if (!user.getPassword().equals(repeatPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "register";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "home";
    }

    @PostMapping("/proceso_login")
    public String login(User user, RedirectAttributes redirectAttributes) {
        User storedUser = userRepo.findByUsername(user.getUsername());
        if (storedUser != null && passEncoder.matches(user.getPassword(), storedUser.getPassword())) {
            return "home";
        } else {
            redirectAttributes.addFlashAttribute("error", "Nombre de usuario o contraseña incorrectos");
            return "redirect:/general/login";
        }
    }
    
}
