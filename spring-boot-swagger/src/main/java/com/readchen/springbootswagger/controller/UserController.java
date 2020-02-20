package com.readchen.springbootswagger.controller;

import com.readchen.springbootswagger.entity.User;
import com.readchen.springbootswagger.vo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "User 操作api",tags = "用户操作接口")
public class UserController {

    @GetMapping("/user/{userID}")
    @ApiOperation(value = "根据id获取用户详情",notes = "例如 /user/1001")
    @ApiImplicitParam(name = "userID",value = "用户id",required = true)
    JsonResult<User> index(@RequestParam("userID") Long userID){

        User user = new User();
        user.setAge(18);
        user.setName("yun");

        return JsonResult.success(user);
    }

    @GetMapping("/users")
    @ApiOperation(value = "获取所有用户信息",notes = "获取所有用户信息")
    JsonResult<List<User>> users(){

        User user = new User();
        user.setAge(18);
        user.setName("yun");

        List<User> users = new ArrayList<User>();
        users.add(user);

        return JsonResult.success(users);
    }

}
