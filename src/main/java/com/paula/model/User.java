package com.paula.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user;
    private Integer role_id;
    private String name;
    private String username;
    private String email;
    private String shipping_address;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Books> books;

    @OneToMany(mappedBy = "bidder")
    private List<Exchanges> exchangesAsBidder;

    @OneToMany(mappedBy = "applicant")
    private List<Exchanges> exchangesAsApplicant;

    public User() {
    }

    public User(Integer id_user, Integer role_id, String name, String username, String email, String shipping_address,
            String password, List<Books> books, List<Exchanges> exchangesAsBidder,
            List<Exchanges> exchangesAsApplicant) {
        this.id_user = id_user;
        this.role_id = role_id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.shipping_address = shipping_address;
        this.password = password;
        this.books = books;
        this.exchangesAsBidder = exchangesAsBidder;
        this.exchangesAsApplicant = exchangesAsApplicant;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    public List<Exchanges> getExchangesAsBidder() {
        return exchangesAsBidder;
    }

    public void setExchangesAsBidder(List<Exchanges> exchangesAsBidder) {
        this.exchangesAsBidder = exchangesAsBidder;
    }

    public List<Exchanges> getExchangesAsApplicant() {
        return exchangesAsApplicant;
    }

    public void setExchangesAsApplicant(List<Exchanges> exchangesAsApplicant) {
        this.exchangesAsApplicant = exchangesAsApplicant;
    }
}
