package com.paula.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookId")
    private Integer bookId;

    @Column(name = "title")
    private String title;

    @Min(value = 1000, message = "El año debe tener 4 dígitos")
    @Max(value = 9999, message = "El año debe tener 4 dígitos")
    @Column(name = "year")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "author")
    private String author;

    @Pattern(regexp = "\\d{13}", message = "El ISBN debe tener 13 dígitos")
    @Column(name = "ISBN")
    private String ISBN;

    @Column(name = "genre")
    private Genre genre;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    private boolean disponible = false;

    @Column(name = "bookCondition")
    private BookCondition condition;

    public Integer getId() {
        return bookId;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
