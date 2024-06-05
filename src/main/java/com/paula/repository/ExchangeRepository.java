package com.paula.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paula.model.Exchange;
import com.paula.model.ExchangeCondition;
import com.paula.model.User;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {
    List<Exchange> findByEstadoAndUsuarioPublicador(ExchangeCondition estado, User usuarioPublicador);

    List<Exchange> findByEstadoAndUsuarioSolicitante(ExchangeCondition estado, User usuarioSolicitante);

    @Transactional
    @Modifying
    @Query("DELETE FROM Exchange ex WHERE ex.libroSolicitado.id = :bookId OR ex.libroPorIntercambiar.id = :bookId")
    void deleteExchangeByBookId(Integer bookId);
}
