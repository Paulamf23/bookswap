package com.paula.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.model.Book;
import com.paula.repository.*;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public ArrayList<Book> getBooks() {
        ArrayList<Book> catalogue = new ArrayList<Book>();
        catalogue = (ArrayList<Book>) bookRepository.findAll();
        return catalogue;
    }

    public Book getBook(int bookId) {
        Book getBook = null;
        Optional<Book> optional = bookRepository.findById(bookId);
        getBook = optional.get();

        return getBook;
    }

    public void createBook(Book book) {
        bookRepository.save(book);
    }

    public void removeBook(Book book) {
        bookRepository.delete(book);
    }

    public ArrayList<Book> search(String searchBox) {
        ArrayList<Book> books = new ArrayList<Book>();
        books = bookRepository.search(searchBox);
        return books;
    }
}
