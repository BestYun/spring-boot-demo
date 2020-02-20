package com.readchen.springbootapidoc.controller;

import com.readchen.springbootapidoc.entity.User;
import com.readchen.springbootapidoc.vo.JsonResult;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api2Doc(id = "user",order = 1,name = "用户操作接口")
@ApiComment(seeClass = User.class)
@RestController
public class UserController {

    @ApiComment("test")
    @GetMapping("/test")
    String test(){
        return "test";
    }

    @GetMapping("/user/{userID}")
    @ApiComment(value = "根据id获取用户详情",sample = "例如 /user/1001",seeClass = User.class)
    User getUserByID(
            @ApiComment(seeField = "userID",value = "用户id")
            @PathVariable("userID") Long userID){

        User user = new User();
        user.setAge(18);
        user.setName("yun");

        return user;
    }

    @GetMapping("/users")
    @ApiComment(value = "获取所有用户信息",sample = "获取所有用户信息")
    JsonResult<List<User>> users(){

        User user = new User();
        user.setAge(18);
        user.setName("yun");

        List<User> users = new ArrayList<User>();
        users.add(user);

        return JsonResult.success(users);
    }

}
