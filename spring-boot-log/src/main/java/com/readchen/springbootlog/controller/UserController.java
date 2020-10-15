package com.readchen.springbootlog.controller;

import com.readchen.springbootlog.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @GetMapping("/users")
    Map<String,Object> users(){

        List<User> users = new ArrayList<User>();
        for (int i=0;i<5;i++){
            User user = new User();
            user.setAge(10*(i+1));
            user.setUsername("yun"+i);
            users.add(user);
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("code",1);
        result.put("data",users);
        return result;
    }
}
