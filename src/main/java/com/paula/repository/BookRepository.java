package com.paula.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paula.model.*;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByUser(User user);

    public void deleteById(Integer bookId);

    List<Book> findTop10ByOrderByBookIdDesc();

    @Query("SELECT b FROM Book b WHERE b.user.username <> :username ORDER BY b.bookId DESC")
    List<Book> findTop10ByOrderByBookIdDescExcludingUser(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM FavouriteBooks fb WHERE fb.book.id = :bookId")
    void deleteFavouriteBooksByBookId(Integer bookId);

    @Transactional
    @Modifying
    @Query("DELETE FROM FavouriteBooks fb WHERE fb.user.id = :userId AND fb.book.id = :bookId")
    void deleteByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> findByTitleIgnoreCase(@Param("title") String title);

    List<Book> findByGenre(Genre genre);

    @Query("SELECT b FROM Book b WHERE b.genre = :genre AND b.user.username <> :username")
    List<Book> findByGenreExcludingUser(@Param("genre") Genre genre, @Param("username") String username);
}
