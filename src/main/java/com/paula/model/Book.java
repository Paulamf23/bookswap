package com.paula.model;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "year")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "id_genre")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "id_condition")
    private BookCondition condition;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "ISBN")
    private String ISBN;

    public Book() {
    }

    public Book(Integer bookId, Integer year, Genre genre, BookCondition condition, User user, String title,
            String author, String iSBN) {
        this.bookId = bookId;
        this.year = year;
        this.genre = genre;
        this.condition = condition;
        this.user = user;
        this.title = title;
        this.author = author;
        ISBN = iSBN;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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

    
}
