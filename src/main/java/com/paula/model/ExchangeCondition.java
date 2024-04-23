package com.paula.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "exchange_condition")
public class ExchangeCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_condition")
    private Integer idCondition;
    
    @Column(name = "condition_name")
    private String conditionName; 

    @OneToMany(mappedBy = "exchangeCondition")
    private List<Exchange> exchange;

    public ExchangeCondition() {}

    public ExchangeCondition(Integer id_condition, String condition_name) {
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

    public List<Exchange> getExchange() {
        return exchange;
    }

    public void setExchange(List<Exchange> exchange) {
        this.exchange = exchange;
    }
}
