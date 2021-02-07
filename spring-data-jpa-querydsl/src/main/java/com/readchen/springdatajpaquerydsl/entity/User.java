package com.readchen.springdatajpaquerydsl.entity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自动增长
    private int uid;
    private String name;
    private String password;
    private String phone;
    private Integer age;


}

