package com.paula.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "exchange")
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_publicador_id", referencedColumnName = "userId")
    private User usuarioPublicador;

    @ManyToOne
    @JoinColumn(name = "usuario_solicitante_id", referencedColumnName = "userId")
    private User usuarioSolicitante;

    @OneToOne
    @JoinColumn(name = "libro_solicitado_id", referencedColumnName = "bookId")
    private Book libroSolicitado;

    @OneToOne
    @JoinColumn(name = "libro_por_intercambiar_id", referencedColumnName = "bookId")
    private Book libroPorIntercambiar;

    @Enumerated(EnumType.STRING)
    private ExchangeCondition estado;
}
