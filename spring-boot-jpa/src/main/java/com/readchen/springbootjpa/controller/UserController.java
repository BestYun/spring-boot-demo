package com.readchen.springbootjpa.controller;

import com.readchen.springbootjpa.model.User;
import com.readchen.springbootjpa.repository.UserRepository;
import com.readchen.springbootjpa.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{userID}")
    JsonResult getUser(@PathVariable("userID") int userID){
        Optional<User> user = userRepository.findById(userID);
        return JsonResult.success(user);
    }

    @PostMapping("/user")
    JsonResult createUser(@PathParam("useranme") String username,
                          @PathParam("password") String password
                          ){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        User u = userRepository.save(user);
        return JsonResult.success(u);
    }

}
