package com.readchen.springdatajpaquerydsl.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private int id;
    private String name;
    private String password;
    private String phone;
    private Integer age;
    private List<ArticleDTO> articles;
}
