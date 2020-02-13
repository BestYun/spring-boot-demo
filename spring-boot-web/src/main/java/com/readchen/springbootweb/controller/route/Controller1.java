package com.readchen.springbootweb.controller.route;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/route")
public class Controller1 {
    @RequestMapping(value = "/v1",method = RequestMethod.GET)
    String get(){
        return "Controller ResponseBody RequestMapping /route/v1 get";
    }
}
