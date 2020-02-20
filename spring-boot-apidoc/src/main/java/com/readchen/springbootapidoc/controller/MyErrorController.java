package com.readchen.springbootapidoc.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyErrorController implements ErrorController {
    private final String Error_Path = "/error";
    @Override
    public String getErrorPath() {
        return Error_Path;
    }

    @RequestMapping(Error_Path)
    String handleError(){
        return  "error";
    }

}
