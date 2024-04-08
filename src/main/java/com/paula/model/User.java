package com.paula.model;

public class User {
    private Integer id_user;
    private String name;
    private String username;
    private String email;
    private String shipping_address;
    private String user_type;
    private String password;
    
    public User() {}

    public User(Integer id_user, String name, String username, String email, String shipping_address, String user_type,
            String password) {
        this.id_user = id_user;
        this.name = name;
        this.username = username;
        this.email = email;
        this.shipping_address = shipping_address;
        this.user_type = user_type;
        this.password = password;
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
