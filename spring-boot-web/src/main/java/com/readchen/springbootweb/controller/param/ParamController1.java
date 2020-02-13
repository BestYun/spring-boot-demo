package com.readchen.springbootweb.controller.param;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/param")
public class ParamController1 {

    @GetMapping("/user/{userID}")
    String getUser(@PathVariable("userID") int userID){
        return "user info... userID = "+userID;
    }
}
