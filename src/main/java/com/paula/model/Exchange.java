package com.paula.model;

import javax.persistence.*;

@Entity
@Table(name = "exchange")
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exchangeId;

    @ManyToOne
    @JoinColumn(name = "id_user_bidder")
    private User bidder;

    @ManyToOne
    @JoinColumn(name = "id_user_applicant")
    private User applicant;

    @ManyToOne
    @JoinColumn(name = "book_id_bidder")
    private Book bookBidder;

    @ManyToOne
    @JoinColumn(name = "book_id_applicant")
    private Book bookApplicant;

    @ManyToOne
    @JoinColumn(name = "exchange_condition")
    private ExchangeCondition exchangeCondition;

    public Exchange() {
    }

    public Exchange(Integer exchange_id, User bidder, User applicant, Book bookBidder, Book bookApplicant,
            ExchangeCondition exchangeCondition) {
        this.exchangeId = exchange_id;
        this.bidder = bidder;
        this.applicant = applicant;
        this.bookBidder = bookBidder;
        this.bookApplicant = bookApplicant;
        this.exchangeCondition = exchangeCondition;
    }

    public Integer getExchange_id() {
        return exchangeId;
    }

    public void setExchange_id(Integer exchange_id) {
        this.exchangeId = exchange_id;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public Book getBookBidder() {
        return bookBidder;
    }

    public void setBookBidder(Book bookBidder) {
        this.bookBidder = bookBidder;
    }

    public Book getBookApplicant() {
        return bookApplicant;
    }

    public void setBookApplicant(Book bookApplicant) {
        this.bookApplicant = bookApplicant;
    }

    public ExchangeCondition getExchangeCondition() {
        return exchangeCondition;
    }

    public void setExchangeCondition(ExchangeCondition exchangeCondition) {
        this.exchangeCondition = exchangeCondition;
    }
}
