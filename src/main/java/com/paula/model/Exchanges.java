package com.paula.model;

import jakarta.persistence.*;

@Entity
@Table(name = "exchanges")
public class Exchanges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exchange_id;
    private Integer id_user_bidder;
    private Integer id_user_applicant;
    private Integer book_id_bidder;
    private Integer book_id_applicant;
    
    @ManyToOne
    @JoinColumn(name = "exchange_condition")
    private ExchangeCondition exchangeCondition;

    @ManyToOne
    @JoinColumn(name = "id_user_order")
    private User user_order;

    public Exchanges() {
    }
    
    public Exchanges(Integer exchange_id, Integer id_user_bidder, Integer id_user_applicant, Integer book_id_bidder,
            Integer book_id_applicant, ExchangeCondition exchangeCondition, User user_order) {
        this.exchange_id = exchange_id;
        this.id_user_bidder = id_user_bidder;
        this.id_user_applicant = id_user_applicant;
        this.book_id_bidder = book_id_bidder;
        this.book_id_applicant = book_id_applicant;
        this.exchangeCondition = exchangeCondition;
        this.user_order = user_order;
    }

    public Integer getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(Integer exchange_id) {
        this.exchange_id = exchange_id;
    }

    public Integer getId_user_bidder() {
        return id_user_bidder;
    }

    public void setId_user_bidder(Integer id_user_bidder) {
        this.id_user_bidder = id_user_bidder;
    }

    public Integer getId_user_applicant() {
        return id_user_applicant;
    }

    public void setId_user_applicant(Integer id_user_applicant) {
        this.id_user_applicant = id_user_applicant;
    }

    public Integer getBook_id_bidder() {
        return book_id_bidder;
    }

    public void setBook_id_bidder(Integer book_id_bidder) {
        this.book_id_bidder = book_id_bidder;
    }

    public Integer getBook_id_applicant() {
        return book_id_applicant;
    }

    public void setBook_id_applicant(Integer book_id_applicant) {
        this.book_id_applicant = book_id_applicant;
    }

    public ExchangeCondition getExchangeCondition() {
        return exchangeCondition;
    }

    public void setExchangeCondition(ExchangeCondition exchangeCondition) {
        this.exchangeCondition = exchangeCondition;
    }

    public User getUser_order() {
        return user_order;
    }

    public void setUser_order(User user_order) {
        this.user_order = user_order;
    }  
}
