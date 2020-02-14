package com.readchen.springbootvalidate.controller;

import com.readchen.springbootvalidate.model.User;
import com.readchen.springbootvalidate.vo.JsonResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@Validated
public class ValidateGetParamController {

    @GetMapping("/param/user")
    JsonResult getUsers(@Validated User user){
        return JsonResult.success(user);
    }


}
