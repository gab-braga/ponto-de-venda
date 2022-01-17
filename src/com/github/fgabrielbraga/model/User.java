package com.github.fgabrielbraga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "NAME", nullable = false, length = 80)
    private String name;

    @Column(name = "PASSWORD", nullable = false, length = 20)
    private String password;

    @Column(name = "ACCESS", nullable = false, length = 15)
    private String permission;

    public User() {
    }

    public User(String name, String password, String permission) {
        this.name = name;
        this.password = password;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
