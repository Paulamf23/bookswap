package com.paula.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.paula.model.Book;
import com.paula.model.User;
import com.paula.repository.BookRepository;



@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public void createBook(Book book) {
        bookRepository.save(book);
    }

    public List<Book> getBooksByUser(User user) {
        return bookRepository.findByUser(user);
    }

    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
    }
    
    public Book getBookById(Integer bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        return optionalBook.orElse(null);
    }
    
}
