package com.paula.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "book_condition")
public class BookCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_condition") 
    private Integer idCondition; 
    
    @Column(name = "condition_name") 
    private String conditionName; 

    @OneToMany(mappedBy = "condition")
    private List<Book> book;

    public BookCondition() {}
    
    public BookCondition(Integer id_condition, String condition_name) {
        this.idCondition = id_condition;
        this.conditionName = condition_name;
    }

    public Integer getId_condition() {
        return idCondition;
    }

    public void setId_condition(Integer id_condition) {
        this.idCondition = id_condition;
    }

    public String getCondition_name() {
        return conditionName;
    }

    public void setCondition_name(String condition_name) {
        this.conditionName = condition_name;
    }

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }
}

