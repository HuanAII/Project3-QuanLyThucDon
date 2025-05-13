package com.example.models;

public class User {
    private String id;
    private String username;
    private String password;
    private String role;
    private String sdt;
    private String email;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String id, String username, String password, String role, String sdt, String email, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.sdt = sdt;
        this.email = email;
    }

    // Getters và Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getSdt() {
        return sdt;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", role=" + role + ", sdt=" + sdt + ", email=" + email + ", address="
                 + "]";
    }
}