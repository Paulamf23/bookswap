package com.paula.repository;

import org.springframework.stereotype.Repository;

// import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

import com.paula.model.Book;
import com.paula.model.User;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

    List<Book> findByUser(User user);

    public void deleteById(Integer bookId);

    List<Book> findTop10ByOrderByBookIdDesc();
}
