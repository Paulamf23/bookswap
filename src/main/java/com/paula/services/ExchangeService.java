package com.paula.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.model.Book;
import com.paula.model.Exchange;
import com.paula.model.ExchangeCondition;
import com.paula.model.User;
import com.paula.repository.BookRepository;
import com.paula.repository.ExchangeRepository;

@Service
public class ExchangeService {
    
    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private BookRepository bookRepository;

    public Exchange createExchange(Exchange exchange) {
        return exchangeRepository.save(exchange);
    }

    public Exchange getExchangeById(Integer id) {
        return exchangeRepository.findById(id).orElse(null);
    }

    public void updateExchange(Exchange exchange) {
        exchangeRepository.save(exchange);
    }

    public List<Exchange> getExchangesByUser(User user) {
        List<Exchange> exchangesAsPublisher = exchangeRepository
                .findByEstadoAndUsuarioPublicador(ExchangeCondition.EnEspera, user);
        List<Exchange> exchangesAsRequester = exchangeRepository
                .findByEstadoAndUsuarioSolicitante(ExchangeCondition.EnEspera, user);
        exchangesAsPublisher.addAll(exchangesAsRequester);
        return exchangesAsPublisher;
    }

    public List<Exchange> getAcceptedExchangesByUser(User user) {
        List<Exchange> exchangesAsPublisher = exchangeRepository
                .findByEstadoAndUsuarioPublicador(ExchangeCondition.Aceptada, user);
        List<Exchange> exchangesAsRequester = exchangeRepository
                .findByEstadoAndUsuarioSolicitante(ExchangeCondition.Aceptada, user);
        exchangesAsPublisher.addAll(exchangesAsRequester);
        return exchangesAsPublisher;
    }

    public List<Exchange> getDeniedExchangesByUser(User user) {
        List<Exchange> exchangesAsPublisher = exchangeRepository
                .findByEstadoAndUsuarioPublicador(ExchangeCondition.Rechazada, user);
        List<Exchange> exchangesAsRequester = exchangeRepository
                .findByEstadoAndUsuarioSolicitante(ExchangeCondition.Rechazada, user);
        exchangesAsPublisher.addAll(exchangesAsRequester);
        return exchangesAsPublisher;
    }

    public void acceptExchange(Integer id) {
        Exchange exchange = getExchangeById(id);
        if (exchange != null) {
            exchange.setEstado(ExchangeCondition.Aceptada);
            updateExchange(exchange);

            Book libroSolicitado = exchange.getLibroSolicitado();
            Book libroPorIntercambiar = exchange.getLibroPorIntercambiar();
            libroSolicitado.setDisponible(true);
            libroPorIntercambiar.setDisponible(true);
            bookRepository.save(libroSolicitado);
            bookRepository.save(libroPorIntercambiar);
        }
    }

    public void rejectExchange(Integer id) {
        Exchange exchange = getExchangeById(id);
        if (exchange != null) {
            exchange.setEstado(ExchangeCondition.Rechazada);
            updateExchange(exchange);
        }
    }

}
