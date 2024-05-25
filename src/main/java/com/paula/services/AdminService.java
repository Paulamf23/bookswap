package com.paula.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.model.Book;
import com.paula.model.User;
import com.paula.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(int userId) {
        User user = userService.getUserById(userId);

        if (user != null) {
            List<Book> userBooks = bookService.getBooksByUser(user);

            for (Book book : userBooks) {
                bookService.deleteBook(book.getId());
            }
            userService.deleteUser(userId);
        }
    }

}
