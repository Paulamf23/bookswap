package com.paula.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "exchange_conditions")
public class ExchangeCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_condition;
    private String condition_name;

    @OneToMany(mappedBy = "exchangeCondition")
    private List<Exchanges> exchanges;

    public ExchangeCondition() {}

    public ExchangeCondition(Integer id_condition, String condition_name) {
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

    public List<Exchanges> getExchanges() {
        return exchanges;
    }

    public void setExchanges(List<Exchanges> exchanges) {
        this.exchanges = exchanges;
    }
}
