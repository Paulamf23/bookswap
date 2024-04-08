package com.paula.model;

public class BookCondition {
    private Integer id_condition;
    private String condition_name;

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
