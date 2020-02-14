package com.readchen.springbootvalidate.controller;

import com.readchen.springbootvalidate.model.User;
import com.readchen.springbootvalidate.vo.JsonResult;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController

public class UserController3 {

    @PostMapping("/user4")
    JsonResult createUser(@RequestBody @Validated User user){

        return JsonResult.success(0, user);
    }
}
