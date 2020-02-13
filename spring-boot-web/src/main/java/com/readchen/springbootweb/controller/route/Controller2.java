package com.readchen.springbootweb.controller.route;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller2 {
    @GetMapping("/v2")
    String v2(){
        return "/route/v2";
    }
}
