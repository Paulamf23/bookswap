package com.paula.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer book_id;
    private Integer year;
    @OneToOne
    @JoinColumn(name = "id_condition")
    private BookCondition condition;
    private Integer id_user;
    private String title;
    private String author;
    private String ISBN;

    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;

    public Books() {
    }

    public Books(Integer book_id, Integer year, BookCondition condition, Integer id_user, String title, String author,
            String ISBN, User user) {
        this.book_id = book_id;
        this.year = year;
        this.condition = condition;
        this.id_user = id_user;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.user = user;
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

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookCondition getCondition() {
        return condition;
    }

    public void setCondition(BookCondition condition) {
        this.condition = condition;
    }   
}
