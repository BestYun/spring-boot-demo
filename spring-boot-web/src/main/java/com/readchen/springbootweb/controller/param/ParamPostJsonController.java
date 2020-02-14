package com.readchen.springbootweb.controller.param;

import com.readchen.springbootweb.controller.validate.User;
import com.readchen.springbootweb.controller.vo.ResultJson;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParamPostJsonController {

    @PostMapping("/param/user/add")
    ResultJson createUser(@RequestBody User user){

        return new ResultJson(0,"create ok success aaa",user);
    }
}
