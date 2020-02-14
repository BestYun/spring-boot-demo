package com.readchen.springbootvalidate.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class User {

    @NotBlank(message = "name不能为空")
    @Length(min = 6,message = "name长度不能小于6")
    private String name;

    @NotNull(message = "age不能为空")
    @Max(value = 200,message = "age不能大于200")
    @Min(value = 0,message = "age不能小于0")
    private Integer age;



    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
