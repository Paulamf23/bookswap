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
        String userEmail = (String) session.getAttribute("email");
        System.out.println("Usuario registrado: " + userEmail);
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/loginUser")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession hSession,
            RedirectAttributes redirect) {
        User userFind = userService.getUser(username);
        if (userFind != null && Encriptation.validatePassword(password, userFind.getPassword())) {
            hSession.setAttribute("email", userFind.getEmail());
            return "redirect:/home";
        } else {
            redirect.addFlashAttribute("errorUsuarioNoExiste", "Usuario o contraseña incorrectos.");
            return "redirect:/login";
        }
    }

    @GetMapping("/perfil")
    public String perfilPage(HttpSession hSession, Model model) {
        if (hSession.getAttribute("email") != null) {
            String email = hSession.getAttribute("email").toString();
            User user = userService.getUser(email);
            hSession.setAttribute("userInfo", user);

            model.addAttribute("username", user.getUsername());

            return "perfil";
        }

        return "redirect:/home";
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
                    user.setEmail(user.getEmail());
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

    @GetMapping("/logOut")
    public String logOut(Model model, HttpSession hSession) {
        if (hSession.getAttribute("email") != null) {
            hSession.setAttribute("email", null);
            return "home";
        }
        return "home";
    }
}
