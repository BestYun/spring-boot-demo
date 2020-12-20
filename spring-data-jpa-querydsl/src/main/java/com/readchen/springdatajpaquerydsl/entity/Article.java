package com.readchen.springdatajpaquerydsl.entity;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "article")
public class Article {

    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自动增长
    private Integer id;

    private String title;

    private String content;

    @Column(name="create_time")
    private int createTime;
    @Column(name = "user_id")
    private int userID;

//    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    private User user;


}
