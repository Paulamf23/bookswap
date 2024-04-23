package com.paula.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer userId;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    private String name;
    
    private String username;
    
    private String email;
    
    private String shippingAddress;
    
    private String password;
    
    @OneToMany(mappedBy = "user")
    private List<Books> books;
    
    @OneToMany(mappedBy = "bidder")
    private List<Exchanges> exchangesAsBidder;
    
    @OneToMany(mappedBy = "applicant")
    private List<Exchanges> exchangesAsApplicant;

    public User() {}

    public User(Integer userId, Role role, String name, String username, String email, String shippingAddress,
            String password, List<Books> books, List<Exchanges> exchangesAsBidder,
            List<Exchanges> exchangesAsApplicant) {
        this.userId = userId;
        this.role = role;
        this.name = name;
        this.username = username;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.password = password;
        this.books = books;
        this.exchangesAsBidder = exchangesAsBidder;
        this.exchangesAsApplicant = exchangesAsApplicant;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
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
