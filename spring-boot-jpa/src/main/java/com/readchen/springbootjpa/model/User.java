package com.readchen.springbootjpa.model;

import javax.persistence.*;

@Entity
public class User {
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自动增长
    private int id;

    private String username;
    private String password;

    public User(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
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


}
