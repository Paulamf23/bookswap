package com.paula.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "book_condition")
public class BookCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_condition;
    private String condition_name;

    @OneToMany(mappedBy = "condition")
    private List<Books> books;

    public BookCondition() {}

    public BookCondition(Integer id_condition, String condition_name) {
        this.id_condition = id_condition;
        this.condition_name = condition_name;
    }

    public Integer getId_condition() {
        return id_condition;
    }

    public void setId_condition(Integer id_condition) {
        this.id_condition = id_condition;
    }

    public String getCondition_name() {
        return condition_name;
    }

    public void setCondition_name(String condition_name) {
        this.condition_name = condition_name;
    }
}
