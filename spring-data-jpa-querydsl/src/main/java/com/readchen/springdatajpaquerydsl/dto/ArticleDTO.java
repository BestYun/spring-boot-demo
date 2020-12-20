package com.readchen.springdatajpaquerydsl.dto;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

import javax.persistence.Column;

@Data
public class ArticleDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer createTime;
    private Integer userID;
}
