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

    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }
    
    @Transactional
    public void deleteBook(Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

		if (book.isPresent()) {
            bookRepository.deleteFavouriteBooksByBookId(bookId);
			bookRepository.deleteById(bookId);
		} else {
			throw new RuntimeException("¡Error! El libro con id " + bookId + " no está en la base de datos.");
		}
    }

    public List<Book> getRecentBooks() {
        return bookRepository.findTop10ByOrderByBookIdDesc();
    }  

    public List<Book> getRecentBooksExcludingUser(String username) {
        return bookRepository.findTop10ByOrderByBookIdDescExcludingUser(username);
    }

    public void updateBook(Book book) {
        bookRepository.save(book); 
    }
}
