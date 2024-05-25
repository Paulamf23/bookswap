package com.paula.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
// import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paula.model.*;
import com.paula.services.AdminService;
import com.paula.services.BookService;
import com.paula.services.ExchangeService;
// import com.paula.services.MessageService;
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

    @Autowired
    private AdminService adminService;

    // @Autowired
    // private MessageService messageService;

    @Autowired
    private ExchangeService exchangeService;

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

    @GetMapping("/bookDetail/{bookId}")
    public String bookDetail(@PathVariable Integer bookId, Model model, HttpSession session) {
        Book book = bookService.getBookById(bookId);
        if (book != null) {
            model.addAttribute("book", book);

            String username = (String) session.getAttribute("username");
            if (username != null) {
                User user = userService.getUserByUsername(username);
                if (user != null && user.getRole() == Role.admin) {
                    model.addAttribute("isAdmin", true);
                } else {
                    model.addAttribute("isAdmin", false);
                }
            } else {
                model.addAttribute("isAdmin", false);
            }

            return "bookDetail";
        }
        return "redirect:/";
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

    @GetMapping("/logOut")
    public String logOut(Model model, HttpSession hSession) {
        if (hSession.getAttribute("username") != null) {
            hSession.setAttribute("username", null);
            return "redirect:/";
        }
        return "home";
    }

    private User getUserFromSession(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return userService.getUserByUsername(username);
        }
        return null;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
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
                redirectAttributes.addFlashAttribute("error",
                        "La contraseña no se corresponde con el usuario introducido");
                return "redirect:/login";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "El usuario introducido no se encuentra en la base de datos");
            return "redirect:/login";
        }
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
                    return "/perfil";
                }
            }
            redirectAttributes.addFlashAttribute("errorUsuarioExiste", "El nombre de usuario ya existe.");
            return "register";
        }
    }

    @GetMapping("/books")
    public String paginaBooks(HttpSession session, Model model) {
        String userUsername = (String) session.getAttribute("username");
        List<Book> books;
        if (userUsername != null) {
            books = bookService.getRecentBooksExcludingUser(userUsername);
        } else {
            books = bookService.getRecentBooks();
        }
        model.addAttribute("books", books);

        List<Genre> genres = Arrays.asList(Genre.values());
        model.addAttribute("genres", genres);

        User user = getUserFromSession(session);
        if (user != null && user.getRole() == Role.admin) {
        }

        return "books";
    }

    @GetMapping("/searchBooks")
    public String searchBooks(@RequestParam("title") String title, HttpSession session, Model model) {
        String userUsername = (String) session.getAttribute("username");
        List<Book> books = bookService.searchBooksByTitle(title);
        model.addAttribute("books", books);
        model.addAttribute("searchQuery", title);

        List<Genre> genres = Arrays.asList(Genre.values());
        model.addAttribute("genres", genres);

        return "books";
    }

    @GetMapping("/books/{genre}")
    public String filterBooksByGenre(@PathVariable String genre, HttpSession session, Model model) {
        String userUsername = (String) session.getAttribute("username");
        List<Book> books;
        if (userUsername != null) {
            books = bookService.getBooksByGenreExcludingUser(genre, userUsername);
        } else {
            books = bookService.getBooksByGenre(genre);
        }
        model.addAttribute("books", books);

        List<Genre> genres = Arrays.asList(Genre.values());
        model.addAttribute("genres", genres);

        return "books";
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

            List<User> allUsers = adminService.getAllUsers();
            model.addAttribute("allUsers", allUsers);

            if (user.getRole() == Role.admin) {
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }

            return "perfil";
        }
        return "redirect:/";
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
            RedirectAttributes redirectAttributes, HttpSession session,
            @RequestParam("genre") Genre genre,
            @RequestParam("condition") BookCondition condition) {
        if (bindingResult.hasErrors()) {
            return "newBookForm";
        } else {
            User user = getUserFromSession(session);
            if (user != null) {
                book.setUser(user);
                book.setGenre(genre);
                book.setCondition(condition);

                bookService.createBook(book);
                return "redirect:/perfil";
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no autenticado");
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/deleteFavorite/{userId}/{bookId}")
    public String deleteFavorite(@PathVariable Integer userId, @PathVariable Integer bookId, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User user = userService.getUserByUsername(username);
            if (user != null && user.getId().equals(userId)) {
                bookService.deleteFavoriteBook(userId, bookId);
                redirectAttributes.addFlashAttribute("success", "Libro eliminado de favoritos correctamente.");
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "No se pudo encontrar al usuario o el usuario no coincide.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para realizar esta acción.");
        }
        return "redirect:/perfil";
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

    @GetMapping("/community")
    public String communityPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username);
            List<Community> messages = userService.getAllMessages();
            Collections.reverse(messages);
            model.addAttribute("messages", messages);

            User user = userService.getUserByUsername(username);
            if (user != null && user.getRole() == Role.admin) {
            }

            if (user.getRole() == Role.admin) {
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }

            return "community";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam String content, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null && !content.isEmpty()) {
            User sender = userService.getUserByUsername(username);
            Community message = new Community();
            message.setContent(content);
            message.setSender(sender);
            userService.saveMessage(message);
        }
        return "redirect:/community";
    }

    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable Integer userId, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteUser(userId);
            redirectAttributes.addFlashAttribute("success", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el usuario.");
        }
        return "redirect:/perfil";
    }

    @GetMapping("/deleteMessage/{id}")
    public String deleteMessage(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("username");
        User user = userService.getUserByUsername(username);
        Community message = userService.getMessageById(id);

        if (user != null && message != null) {
            if (user.getRole() == Role.admin || user.getUsername().equals(message.getSender().getUsername())) {
                userService.deleteMessage(id);
                redirectAttributes.addFlashAttribute("success", "Mensaje eliminado correctamente.");
            } else {
                redirectAttributes.addFlashAttribute("error", "No tienes permiso para eliminar este mensaje.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo encontrar el mensaje o usuario.");
        }
        return "redirect:/community";
    }

    @GetMapping("/elegirIntercambio/{bookId}")
    public String elegirIntercambio(@PathVariable Integer bookId, Model model, HttpSession session) {
        Book book = bookService.getBookById(bookId);
        if (book != null) {
            model.addAttribute("book", book);
            String username = (String) session.getAttribute("username");
            if (username != null) {
                User user = userService.getUserByUsername(username);
                if (user != null) {
                    List<Book> userBooks = bookService.getBooksByUser(user);
                    List<Book> userBooksSameCondition = userBooks.stream()
                            .filter(b -> b.getCondition().equals(book.getCondition()))
                            .collect(Collectors.toList());
                    model.addAttribute("userBooksSameCondition", userBooksSameCondition);
                    model.addAttribute("selectedBook", null);
                }
            }
            return "elegirIntercambio";
        }
        return "redirect:/";
    }

    @GetMapping("/solicitarIntercambio/{bookId}/{userBookId}")
    public String solicitarIntercambio(@PathVariable Integer bookId, @PathVariable Integer userBookId,
            HttpSession session, RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User loggedUser = userService.getUserByUsername(username);
            Book bookToExchange = bookService.getBookById(bookId);
            Book userBook = bookService.getBookById(userBookId);

            if (loggedUser != null && bookToExchange != null && userBook != null) {
                bookService.updateBook(bookToExchange);
                bookService.updateBook(userBook);

                Exchange exchange = new Exchange();
                exchange.setUsuarioPublicador(bookToExchange.getUser());
                exchange.setUsuarioSolicitante(loggedUser);
                exchange.setLibroSolicitado(bookToExchange);
                exchange.setLibroPorIntercambiar(userBook);
                exchange.setEstado(ExchangeCondition.EnEspera);

                exchangeService.createExchange(exchange);

                // Message message = new Message();
                // message.setReceptor(bookToExchange.getUser());
                // message.setContenido("El usuario @" + loggedUser.getUsername() +
                // " quiere cambiar tu libro '" + bookToExchange.getTitle() +
                // "' por su libro '" + userBook.getTitle() + "'. ¿Quieres aceptar el
                // intercambio?");
                // message.setMessageType(MessageType.propuesta);
                // messageService.sendNotification(message);

                redirectAttributes.addFlashAttribute("success", "Intercambio solicitado correctamente.");
                return "redirect:/exchangedetail/" + exchange.getId();
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para solicitar un intercambio.");
        }
        return "redirect:/elegirIntercambio/" + bookId;
    }

    @GetMapping("/exchangedetail/{exchangeId}")
    public String exchangeDetail(@PathVariable Integer exchangeId, Model model) {
        Exchange exchange = exchangeService.getExchangeById(exchangeId);
        if (exchange != null) {
            model.addAttribute("exchange", exchange);
            return "exchangeDetail";
        }
        return "redirect:/";
    }

    @GetMapping("/exchanges")
    public String exchangesPage(HttpSession hSession, Model model) {
        if (hSession.getAttribute("username") != null) {
            String username = hSession.getAttribute("username").toString();
            User user = userService.getUser(username);
            List<Exchange> propuestaExchanges = exchangeService.getExchangesByUser(user);
            List<Exchange> accept = exchangeService.getAcceptedExchangesByUser(user);
            List<Exchange> denied = exchangeService.getDeniedExchangesByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("propuestaExchanges", propuestaExchanges);
            model.addAttribute("accept", accept);
            model.addAttribute("denied", denied);
            model.addAttribute("currentUsername", username);

            return "exchanges";
        }
        return "redirect:/";
    }

    @PostMapping("/accept/{id}")
    public String acceptExchange(@PathVariable Integer id) {
        exchangeService.acceptExchange(id);
        return "redirect:/exchanges";
    }

    @PostMapping("/reject/{id}")
    public String rejectExchange(@PathVariable Integer id) {
        exchangeService.rejectExchange(id);
        return "redirect:/exchanges";
    }

}