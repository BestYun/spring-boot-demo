package com.readchen.springdatajpaquerydsl.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserArticleDTO {
    private int uid;
    private String name;
    private String password;
    private String phone;
    private Integer age;

    private Integer id;
    private String title;
    private String content;
    private Integer createTime;
}
