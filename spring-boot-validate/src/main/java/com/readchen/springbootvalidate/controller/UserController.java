package com.readchen.springbootvalidate.controller;

import com.readchen.springbootvalidate.vo.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @PostMapping("/user")
    JsonResult createUser(){

        return JsonResult.success(0,"");
    }

}
