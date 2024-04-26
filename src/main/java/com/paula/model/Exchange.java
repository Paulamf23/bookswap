package com.paula.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exchange")
public class Exchange implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchangeId")
    private Integer exchangeId;

    @ManyToOne
    private User user;

    @OneToOne
    private Book book;

    @Column(name = "exchange_condition")
	@Enumerated(EnumType.STRING)
	private ExchangeCondition exchange_condition;

    public Exchange(ExchangeCondition exchange_condition, int exchangeId) {
		this.exchange_condition = exchange_condition;
		this.exchangeId = exchangeId;
	}
	
	public Exchange(User user, ExchangeCondition exchangeCondition, Book book) {
		this.user = user;
		this.exchange_condition = exchangeCondition;
		this.book = book;
	}
}
