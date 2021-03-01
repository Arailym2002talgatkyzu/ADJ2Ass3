package com.example.demo.repository.dto;

import javax.persistence.*;

@Entity(name = "users")
public class User {
    @Id
    @Column(name="id")
    private String username;
    private String password;
    private String name;
    private String surname;
    private Boolean deleted;

    @OneToOne(optional = false)
    @JoinColumn(name="role_id")
    private Role role;

    public User() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
