package com.paula.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.model.Book;
import com.paula.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public void createBook(Book book) {
        bookRepository.save(book);
    }
}
