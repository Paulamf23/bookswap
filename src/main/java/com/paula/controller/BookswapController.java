package com.paula.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    public String home(HttpSession session, Model model) {
        String userUsername = (String) session.getAttribute("username");
        System.out.println("Usuario registrado: " + userUsername);
        List<Book> books = bookService.getRecentBooks();
        model.addAttribute("books", books);
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
                return "redirect:/perfil";
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "La contraseña no se corresponde con el usuario introducido");
                return "redirect:/login";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "El usuario introducido no se encuentra en la base de datos");
            return "redirect:/login";
        }
    }

    @GetMapping("/perfil")
    public String perfilPage(HttpSession hSession, Model model) {
        if (hSession.getAttribute("username") != null) {
            String username = hSession.getAttribute("username").toString();
            User user = userService.getUser(username);
            List<Book> books = bookService.getBooksByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("books", books);
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
                    return "perfil";
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

    @GetMapping("/newBook")
    public String newBookForm(Model model, HttpSession session) {
        User user = getUserFromSession(session);
        if (user != null) {
            Book book = new Book();
            book.setUser(user);
            model.addAttribute("book", book);
            return "newBookForm";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/createBook")
    public String createBook(@Valid @ModelAttribute Book book,
            @RequestParam("image") MultipartFile imageFile,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "newBookForm";
        } else {
            User user = getUserFromSession(session);
            if (user != null) {
                book.setUser(user);
                byte[] imageData = convertImageToBytes(imageFile);
                book.setImageData(imageData);
                bookService.createBook(book);
                return "redirect:/perfil";
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no autenticado");
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/image/{bookId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer bookId) {
        byte[] imageData = bookService.getImageData(bookId);
        if (imageData != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageData.length);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private byte[] convertImageToBytes(MultipartFile imageFile) {
        try {
            return imageFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User getUserFromSession(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return userService.getUserByUsername(username);
        }
        return null;
    }

    @GetMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable Integer bookId, RedirectAttributes redirectAttributes) {
        bookService.deleteBook(bookId);
        redirectAttributes.addFlashAttribute("exito", "El libro con id " + bookId + " ha sido eliminado exitosamente.");
        return "redirect:/perfil";

    }

}