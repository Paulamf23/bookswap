package com.paula.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exchange")
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchangeId")
    private Integer exchangeId;

    @ManyToOne
    @JoinColumn(name = "id_user_bidder")
    private User bidder;

    @ManyToOne
    @JoinColumn(name = "id_user_applicant")
    private User applicant;

    @OneToOne
    @JoinColumn(name = "book_id_bidder")
    private Book bookBidder;

    @OneToOne
    @JoinColumn(name = "book_id_applicant")
    private Book bookApplicant;

    @Column(name = "exchange_condition")
	@Enumerated(EnumType.STRING)
	private ExchangeCondition exchange_condition;

    public Exchange(ExchangeCondition exchange_condition, int exchangeId) {
		this.exchange_condition = exchange_condition;
		this.exchangeId = exchangeId;
	}
	
	public Exchange(User bidder, User applicant, ExchangeCondition exchangeCondition, Book bookBidder, Book bookAplicant) {
		this.bidder = bidder;
        this.applicant = applicant;
		this.exchange_condition = exchangeCondition;
		this.bookBidder = bookBidder;
		this.bookApplicant = bookAplicant;
	}
}
