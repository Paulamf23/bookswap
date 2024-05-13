package com.paula.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
    
	@Transactional
    public void deleteBookById(Integer bookId) {
        
        Optional<Book> book = bookRepository.findById(bookId);
		if (book.isPresent()) {
			bookRepository.deleteById(bookId);
		} else {
			throw new RuntimeException("¡Error! El libro con id " + bookId + " no se encuentra en la base de datos.");
		}
    }
    
}
