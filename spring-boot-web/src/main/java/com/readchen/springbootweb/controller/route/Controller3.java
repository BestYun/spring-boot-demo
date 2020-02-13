package com.readchen.springbootweb.controller.route;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route")
public class Controller3 {

    @GetMapping("/v3")
    String v3(){
        return "/route/v3";
    }

    @GetMapping("/v4")
    String index(){
        return "/route/v4";
    }

}
