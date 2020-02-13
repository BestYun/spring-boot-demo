package com.readchen.springbootweb.controller.validate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class User {
    @NotNull(message = "不能为空")
    private String username;
    private String password;

    @Max(value = 200,message = "年龄不能大于200")
    @Min(value = 0,message = "不能小于0")
    private int age;
    @Email
    private String email;

}
