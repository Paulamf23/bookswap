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

import com.paula.model.Book;
import com.paula.model.Role;
import com.paula.model.User;
import com.paula.services.BookService;
import com.paula.services.UserService;
import com.paula.util.Encriptation;

@Controller
@RequestMapping("/")
public class BookswapController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String home(HttpSession session) {
        String userUsername = (String) session.getAttribute("username");
        System.out.println("Usuario registrado: " + userUsername);
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
        User existingUser = userService.getUserByUsername(user.getUsername());

        if (existingUser != null) {
            if (Encriptation.validatePassword(user.getPassword(), existingUser.getPassword())) {
                session.setAttribute("username", existingUser.getUsername());
                return "redirect:/";
            } else {
                redirectAttributes.addFlashAttribute("loginError", "Contraseña incorrecta");
                return "redirect:/login";
            }
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Usuario no encontrado");
            return "redirect:/login";
        }
    }

    @GetMapping("/perfil")
    public String perfilPage(HttpSession hSession, Model model) {
        if (hSession.getAttribute("username") != null) {
            String username = hSession.getAttribute("username").toString();
            User user = userService.getUser(username);
            model.addAttribute("user", user);
            return "perfil";
        }
        return "redirect:/";
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
            User existingUser = userService.getUser(user.getUsername());
            if (existingUser != null) {
                redirectAttributes.addFlashAttribute("errorUsuarioExiste", "El nombre de usuario ya está registrado.");
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
        if (hSession.getAttribute("username") != null) {
            hSession.setAttribute("username", null);
            return "home";
        }
        return "home";
    }

    @GetMapping("/myBooks")
    public String myBooksPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User user = userService.getUserByUsername(username);
            model.addAttribute("user", user);
            return "myBooks"; 
        } else {
            return "redirect:/login"; 
        }
    }

    @GetMapping("/newBook")
    public String newBookForm(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        System.out.println("Entra en el metodo");
        if (username != null) {
            User user = userService.getUserByUsername(username);
            Book book = new Book();
            book.setUser(user);
            model.addAttribute("book", book);
            System.out.println("Coge al usuario para ponerle el libro");
            return "newBookForm"; 
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/createBook")
    public String createBook(@Valid @ModelAttribute Book book, BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "newBookForm";
        } else {
            bookService.createBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Libro publicado exitosamente");
            return "redirect:/myBooks";
        }
    }

}
