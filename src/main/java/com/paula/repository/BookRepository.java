package com.paula.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.paula.model.Book;

public interface BookRepository extends JpaRepository <Book, Integer>{

    @Query("select b from Book b where b.title like %?1%")
	ArrayList<Book> search(String searchBox);

}
