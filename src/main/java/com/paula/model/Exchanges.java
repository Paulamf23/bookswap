package com.paula.model;

import javax.persistence.*;

@Entity
@Table(name = "exchanges")
public class Exchanges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exchange_id;

    @ManyToOne
    @JoinColumn(name = "id_user_bidder")
    private User bidder;

    @ManyToOne
    @JoinColumn(name = "id_user_applicant")
    private User applicant;

    @ManyToOne
    @JoinColumn(name = "book_id_bidder")
    private Books bookBidder;

    @ManyToOne
    @JoinColumn(name = "book_id_applicant")
    private Books bookApplicant;

    @ManyToOne
    @JoinColumn(name = "exchange_condition")
    private ExchangeCondition exchangeCondition;

    public Exchanges() {
    }

    public Exchanges(Integer exchange_id, User bidder, User applicant, Books bookBidder, Books bookApplicant,
            ExchangeCondition exchangeCondition) {
        this.exchange_id = exchange_id;
        this.bidder = bidder;
        this.applicant = applicant;
        this.bookBidder = bookBidder;
        this.bookApplicant = bookApplicant;
        this.exchangeCondition = exchangeCondition;
    }

    public Integer getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(Integer exchange_id) {
        this.exchange_id = exchange_id;
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

    public Books getBookBidder() {
        return bookBidder;
    }

    public void setBookBidder(Books bookBidder) {
        this.bookBidder = bookBidder;
    }

    public Books getBookApplicant() {
        return bookApplicant;
    }

    public void setBookApplicant(Books bookApplicant) {
        this.bookApplicant = bookApplicant;
    }

    public ExchangeCondition getExchangeCondition() {
        return exchangeCondition;
    }

    public void setExchangeCondition(ExchangeCondition exchangeCondition) {
        this.exchangeCondition = exchangeCondition;
    }
}
