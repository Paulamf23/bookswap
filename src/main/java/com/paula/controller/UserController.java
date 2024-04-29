package com.paula.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paula.model.Role;
import com.paula.model.User;
import com.paula.services.UserService;
import com.paula.util.Encriptation;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String home(HttpSession session) {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/loginUser")
    public String login(@Valid @ModelAttribute User user, BindingResult bindigResult, HttpSession hSession, Model model,
            RedirectAttributes redirect) {
        if (bindigResult.hasErrors()) {
            model.addAttribute("user", user);
            return "login";
        }
        if (user.getEmail() != null && user.getPassword() != null) {
            for (User userFind : userService.getUsers()) {
                if (userFind.getEmail().equals(user.getEmail())
                        && Encriptation.validatePassword(user.getPassword(), userFind.getPassword()) == true) {
                    hSession.setAttribute("email", user.getEmail());
                    return "home";
                }
            }
        }

        redirect.addFlashAttribute("errorUsuarioNoExiste", "Usuario o contraseña incorrectos.");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/newUser")
    public String newUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(name = "repeatPassword", required = false) String repeatedPassword,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            User existingUser = userService.getUser(user.getEmail());
            if (existingUser != null) { 
                redirectAttributes.addFlashAttribute("errorUsuarioExiste", "El correo electrónico ya está registrado.");
                return "redirect:/register"; 
            } else {
                if (user.getEmail() != null && user.getPassword() != null && repeatedPassword != null &&
                        user.getName() != null && user.getUsername() != null
                        && user.getPassword().equals(repeatedPassword)) {
                    Role role = Role.registeredUser;
                    user.setRole(role);
                    String encryptedPassword = Encriptation.encriptPassword(user.getPassword());
                    user.setPassword(encryptedPassword);
                    user.setName(user.getName());
                    user.setUsername(user.getUsername());
                    userService.createUser(user);
                    session.setAttribute("email", user.getEmail());
                    session.setAttribute("name", user.getName());
                    session.setAttribute("username", user.getUsername());
                    return "home";
                }
            }
            redirectAttributes.addFlashAttribute("errorUsuarioExiste", "El nombre de usuario ya existe.");
            return "register";
        }
    }

}
