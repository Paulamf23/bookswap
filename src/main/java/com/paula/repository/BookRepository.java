package com.paula.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paula.model.Book;
import com.paula.model.User;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByUser(User user);

    public void deleteById(Integer bookId);

    List<Book> findTop10ByOrderByBookIdDesc();

    @Query("SELECT b FROM Book b WHERE b.user.username <> :username ORDER BY b.bookId DESC")
    List<Book> findTop10ByOrderByBookIdDescExcludingUser(@Param("username") String username);
}
