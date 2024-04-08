package com.paula.model;

public class Books {
    private Integer book_id;
    private Integer year;
    private Integer id_condition;
    private Integer id_user;
    private String title;
    private String author;
    private String ISBN;
    
    public Books() {
    }

    public Books(Integer book_id, Integer year, Integer id_condition, Integer id_user, String title, String author,
            String iSBN) {
        this.book_id = book_id;
        this.year = year;
        this.id_condition = id_condition;
        this.id_user = id_user;
        this.title = title;
        this.author = author;
        ISBN = iSBN;
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

    public Integer getId_condition() {
        return id_condition;
    }

    public void setId_condition(Integer id_condition) {
        this.id_condition = id_condition;
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
}
