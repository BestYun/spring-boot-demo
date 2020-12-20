package com.readchen.springdatajpaquerydsl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class IndexController {
    @GetMapping("/")
    String index(){
        return "spring boot jpg querydsl";
    }

}
