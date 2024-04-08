package com.paula.model;

import java.util.*;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String name;
    private String username;
    private String email;
    private String shipping_address;
    private String password;
    
    @OneToMany(mappedBy = "user")
    private List<Books> books;

    @OneToMany(mappedBy = "user_order")
    private List<Exchanges> exchanges;

    public User() {}

    public User(Integer id_user, Role role, String name, String username, String email, String shipping_address,
            String password, List<Books> books, List<Exchanges> exchanges) {
        this.id_user = id_user;
        this.role = role;
        this.name = name;
        this.username = username;
        this.email = email;
        this.shipping_address = shipping_address;
        this.password = password;
        this.books = books;
        this.exchanges = exchanges;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
