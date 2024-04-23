package com.paula.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String username;
    
    @Column(unique = true)
    private String email;
    
    private String shippingAddress;
    
    @Column(nullable = false, length = 64)
    private String password;
    
    @OneToMany(mappedBy = "user")
    private List<Book> book;
    
    @OneToMany(mappedBy = "bidder")
    private List<Exchange> exchangeAsBidder;
    
    @OneToMany(mappedBy = "applicant")
    private List<Exchange> exchangeAsApplicant;

    public User() {}

    public User(Integer userId, Role role, String name, String username, String email, String shippingAddress,
            String password, List<Book> book, List<Exchange> exchangeAsBidder,
            List<Exchange> exchangeAsApplicant) {
        this.userId = userId;
        this.role = role;
        this.name = name;
        this.username = username;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.password = password;
        this.book = book;
        this.exchangeAsBidder = exchangeAsBidder;
        this.exchangeAsApplicant = exchangeAsApplicant;
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

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }

    public List<Exchange> getExchangeAsBidder() {
        return exchangeAsBidder;
    }

    public void setExchangeAsBidder(List<Exchange> exchangeAsBidder) {
        this.exchangeAsBidder = exchangeAsBidder;
    }

    public List<Exchange> getExchangeAsApplicant() {
        return exchangeAsApplicant;
    }

    public void setExchangeAsApplicant(List<Exchange> exchangeAsApplicant) {
        this.exchangeAsApplicant = exchangeAsApplicant;
    }
}
