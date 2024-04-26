// package com.paula.controller;

// import java.util.ArrayList;

// import javax.servlet.http.HttpSession;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.paula.model.Book;
// import com.paula.services.BookService;

// @Controller
// @RequestMapping("")
// public class BookController {

//     @Autowired
//     BookService bookService;

//     @SuppressWarnings("unchecked")
//     @GetMapping("")
//     public String inicio(Model model, HttpSession hSession) {
//         ArrayList<Book> books = bookService.getBooks();
//         model.addAttribute("books", books);
//         Object favouriteSession = hSession.getAttribute("favourites");
//         ArrayList<Book> favourite;

//         if (favouriteSession == null) {
//             favourite = new ArrayList<Book>();
//         } else {
//             favourite = (ArrayList<Book>) favouriteSession;
//         }

//         hSession.setAttribute("favourite", favourite);
//         return "home";
//     }

//     @GetMapping("/addBookToFav")
//     public String addBookToFav(HttpSession hSession, @RequestParam(name = "bookId", required = false) int id) {
//         @SuppressWarnings("unchecked")
//         ArrayList<Book> favourite = (ArrayList<Book>) hSession.getAttribute("favourite");
//         Book book;
//         book = bookService.getBook(id);
//         favourite.add(book);
//         hSession.setAttribute("favourite", favourite);

//         return "home";
//     }

//     @GetMapping("/searchBook")
//     public String searchBook(Model model, HttpSession hSession,
//             @RequestParam(name = "search", required = false) String searchBox) {
//         if (searchBox != null) {
//             ArrayList<Book> wantedBooks = new ArrayList<Book>();
//             wantedBooks = bookService.search(searchBox);
//             hSession.setAttribute("wantedBooks", wantedBooks);
//             model.addAttribute("catalog", wantedBooks);
//             return "home";
//         }

//         return "home";
//     }

//     @GetMapping("/anonimous/BookDetail/{id}")
//     public String bookDetail(@PathVariable("bookId") Integer id, HttpSession hSession) {
//         Book detailBook = bookService.getBook(id);
//         hSession.setAttribute("detailBook", detailBook);
//         return "anonimous/detailBook";
//     }
// }