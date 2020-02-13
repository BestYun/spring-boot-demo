package com.readchen.springbootweb.controller.param;

import com.readchen.springbootweb.controller.vo.ResultJson;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ParamPostController {

    @PostMapping("param/user/{userID}")
    ResultJson getUser(@PathVariable("userID") int userID){

        return new ResultJson(0,"ok","post user info... userID = "+userID);
    }


}
