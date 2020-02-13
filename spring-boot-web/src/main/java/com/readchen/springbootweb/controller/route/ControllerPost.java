package com.readchen.springbootweb.controller.route;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerPost {


    @GetMapping("/route/get")
    @PostMapping("route/post")
    String post(){
        return "this is a post request";
    }
}
