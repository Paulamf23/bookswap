package com.paula.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer book_id;
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "id_condition")
    private BookCondition condition;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    private String title;
    private String author;
    private String ISBN;
    private String genre;

    public Books() {
    }

    public Books(Integer book_id, Integer year, BookCondition condition, User user, String title, String author,
            String iSBN, String genre) {
        this.book_id = book_id;
        this.year = year;
        this.condition = condition;
        this.user = user;
        this.title = title;
        this.author = author;
        ISBN = iSBN;
        this.genre = genre;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BookCondition getCondition() {
        return condition;
    }

    public void setCondition(BookCondition condition) {
        this.condition = condition;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
