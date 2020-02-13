package com.readchen.springbootweb.controller.param;

import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/param")
public class ParamController1 {

    @GetMapping("/user/{userID}")
    Object getUser(@PathVariable("userID") int userID){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("errCode",0);
        result.put("msg","ok");
        result.put("data","user info... userID = "+userID);
        return result;
    }


    @GetMapping("/hello")
    String sayHello(@PathParam("username") String username){
        return username + " hello";
    }

//    @RequestParam可以设置默认值
    @GetMapping("/hello2")
    String sayHelloDefault(@RequestParam(value = "username",required = false,defaultValue = "Li yun") String username){
        return username + " hello";
    }

}

