package com.readchen.springbootvalidate.controller;

import com.readchen.springbootvalidate.model.User;
import com.readchen.springbootvalidate.vo.JsonResult;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@RestController
public class UserController {


    @PostMapping("/user")
//    放在统一异常处理
    JsonResult createUser(@Validated User user){
        return JsonResult.success(0,user);
    }

    @PostMapping("/user2")
    JsonResult createUser2(
            @Valid User user,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){

            return JsonResult.fail(1,bindingResult.getFieldError().getDefaultMessage());
        }

        return JsonResult.success(0,user);
    }

}
