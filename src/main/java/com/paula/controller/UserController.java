package com.paula.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paula.model.Book;
// import com.paula.model.Exchange;
import com.paula.model.Role;
import com.paula.model.User;
import com.paula.services.BookService;
// import com.paula.services.ExchangeService;
import com.paula.services.UserService;
import com.paula.util.Encriptation;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    // private ExchangeService exchangeService;

    @GetMapping("/general")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
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
                    bookService.getBooks();
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

    @PostMapping("/register")
    public String newUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(name = "repeatPassword", required = false) String repeatedPassword,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            User existingUser = userService.getUser(user.getEmail());
            if (existingUser == null) {
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

    @GetMapping("/perfil")
    public String perfilPage(HttpSession hSession) {
        if (hSession.getAttribute("email") != null) {
            String email = hSession.getAttribute("email").toString();
            User user = userService.getUser(email);
            hSession.setAttribute("userInfo", user);
            return "perfil";
        }

        return "redirect:/general";
    }

    @GetMapping("/perfil/logOut")
    public String logOut(Model model, HttpSession hSession) {
        if (hSession.getAttribute("email") != null) {
            hSession.setAttribute("email", null);
            return "redirect:/general";
        }
        return "redirect:/perfil";
    }

    // @GetMapping("/perfil/exchanges")
    // public String getExchanges(HttpSession hSession) {
    // if (hSession.getAttribute("email") != null) {
    // ArrayList<Exchange> userExchange = new ArrayList<Exchange>();
    // User user = userService.getUser(hSession.getAttribute("email").toString());
    // userExchange = exchangeService.getUserExchange(user);
    // hSession.setAttribute("exchangeUser", userExchange);
    // return "exchanges";
    // }
    // return "home";
    // }

    @GetMapping("/favourites")
    public String getFavourites(HttpSession hSession) {
        if (hSession.getAttribute("favourites") != null) {
            return "myFavs";
        }
        return "redirect:/general";
    }

    @GetMapping("/favourites/removeFav")
    public String removeFav(HttpSession hSession, @RequestParam(name = "bookId", required = false) int id) {
        @SuppressWarnings("unchecked")
        ArrayList<Book> favourites = (ArrayList<Book>) hSession.getAttribute("favourite");
        Book book = bookService.getBook(id);
        favourites.remove(book);
        hSession.setAttribute("favourite", favourites);

        return "redirect:/myFavs";
    }

    @GetMapping("/books")
    public String getBooks(Model model, HttpSession hSession) {
        if (hSession.getAttribute("adminUser") != null) {
            ArrayList<Book> books = new ArrayList<Book>();
            books = bookService.getBooks();
            hSession.setAttribute("adminBooks", books);
            return "myBooks";
        }

        return "redirect:/general";
    }

    @GetMapping("/perfil/newBook")
    public String newBookButton(Model model) {
        model.addAttribute("book", new Book());
        return "addNewBook";
    }

    @PostMapping("/Book/newBook")
    public String newBook(Model model, @ModelAttribute Book book, HttpSession hSession) {
        if (book.getTitle() != null && book.getAuthor() != null && book.getISBN() != null && book.getYear() != null) {
            bookService.createBook(book);
            return "myBooks";
        }

        return "redirect:/myBooks";
    }

    @GetMapping("/books/removeBook/{id}")
    public String removeBook(HttpSession hSession, @PathVariable(name = "book_id", required = false) int id) {
        Book book = bookService.getBook(id);
        bookService.removeBook(book);
        return "redirect:/myBooks";
    }

}
