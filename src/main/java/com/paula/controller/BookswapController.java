package com.paula.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.paula.model.*;
import com.paula.services.BookService;
import com.paula.services.UserService;
import com.paula.util.Encriptation;

import net.coobird.thumbnailator.Thumbnails;

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
        List<Book> books;
        if (userUsername != null) {
            books = bookService.getRecentBooksExcludingUser(userUsername);
        } else {
            books = bookService.getRecentBooks();
        }
        model.addAttribute("books", books);
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
            List<Book> favoriteBooks = user.getFavorites().stream()
                    .map(FavouriteBooks::getBook)
                    .collect(Collectors.toList());
            model.addAttribute("user", user);
            model.addAttribute("books", books);
            model.addAttribute("favoriteBooks", favoriteBooks);
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
            return "redirect:/";
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
    public String createBook(@Valid @ModelAttribute Book book, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes, HttpSession session, @RequestParam("genre") Genre genre) {
        if (bindingResult.hasErrors()) {
            return "newBookForm";
        } else {
            User user = getUserFromSession(session);
            if (user != null) {
                book.setUser(user);
                book.setGenre(genre);
                bookService.createBook(book);
                return "redirect:/perfil";
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no autenticado");
                return "redirect:/login";
            }
        }
    }

    private User getUserFromSession(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return userService.getUserByUsername(username);
        }
        return null;
    }

    @GetMapping("/addFavorite/{bookId}")
    public String addFavorite(@PathVariable Integer bookId, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User user = userService.getUserByUsername(username);
            Book book = bookService.getBookById(bookId);
            if (user != null && book != null) {
                if (user.getFavorites().stream().noneMatch(fav -> fav.getBook().getId().equals(bookId))) {
                    FavouriteBooks favorite = new FavouriteBooks();
                    favorite.setUser(user);
                    favorite.setBook(book);
                    user.getFavorites().add(favorite);
                    userService.updateUser(user);
                    redirectAttributes.addFlashAttribute("success", "Libro agregado a favoritos correctamente.");
                } else {
                    redirectAttributes.addFlashAttribute("info", "El libro ya está en tus favoritos.");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "No se pudo agregar el libro a favoritos.");
            }
            return "redirect:/perfil";
        } else {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para agregar a favoritos.");
            return "redirect:/login";
        }
    }

    @GetMapping("/deleteBook/{bookId}")
	public String deleteBook(@PathVariable Integer bookId, RedirectAttributes redirectAttributes) {
		bookService.deleteBook(bookId);
		redirectAttributes.addFlashAttribute("exito", "El libro con id " + bookId + " ha sido eliminado exitosamente.");
		return "redirect:/perfil";

	}

    @GetMapping("/uploadImage/{bookId}")
    public String showUploadImageForm(@PathVariable Integer bookId, Model model, HttpSession session) {
        Book book = bookService.getBookById(bookId);
        String username = (String) session.getAttribute("username");
        if (book != null && book.getUser().getUsername().equals(username)) {
            model.addAttribute("book", book);
            return "uploadBookImage";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/books/{bookId}/image")
    public String uploadImage(@PathVariable Integer bookId, @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.getBookById(bookId);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(image.getInputStream()).size(200, 200).outputFormat("jpg").outputQuality(0.5)
                    .toOutputStream(outputStream);
            book.setImage(outputStream.toByteArray());

            bookService.updateBook(book);

            redirectAttributes.addFlashAttribute("success", "Imagen subida correctamente.");
            return "redirect:/perfil";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error al subir la imagen.");
            return "redirect:/perfil";
        }
    }

    @GetMapping(value = "/books/{bookId}/image", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> getImage(@PathVariable Integer bookId) {
        Book book = bookService.getBookById(bookId);
        if (book != null && book.getImage() != null) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(book.getImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bookDetail/{bookId}")
    public String bookDetail(@PathVariable Integer bookId, Model model) {
        Book book = bookService.getBookById(bookId);
        if (book != null) {
            model.addAttribute("book", book);
            return "bookDetail";
        }
        return "redirect:/";
    }

}