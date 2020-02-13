package com.readchen.springbootweb.controller.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ControllerResponse {
    @Autowired
    HttpServletResponse servletResponse;

    @GetMapping("/route/response")
    String response(){
        servletResponse.setStatus(403);
        return "response http code = 403";
    }
}
