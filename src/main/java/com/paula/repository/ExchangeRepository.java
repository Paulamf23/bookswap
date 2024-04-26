package com.paula.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.paula.model.Exchange;
import com.paula.model.ExchangeCondition;
import com.paula.model.User;

public interface ExchangeRepository extends JpaRepository <Exchange, Integer>{

    ArrayList<Exchange> findByUser(User user);
	
	@Transactional
	@Modifying
	@Query("update Exchange exc set exc.exchange_condition = ?1 where exc.exchangeId = ?2")
    void cancel(ExchangeCondition exchangeCondition, int exchangeId);
}
