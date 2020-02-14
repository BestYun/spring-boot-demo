package com.readchen.springbootvalidate.controller;

import com.readchen.springbootvalidate.vo.JsonResult;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotBlank;


@RestController
//在请求方法中校验需要在类上加该注解
@Validated
public class UserController2 {


    @PostMapping("/user3")//    只能通过全局异常捕获参数校验错误信息
    JsonResult createUser(
            @NotBlank(message = "用户名不能为空")
            @Length(min = 6,message = "用户名不能小于6")
                    String name){

        return JsonResult.success(0,name);
    }

}
