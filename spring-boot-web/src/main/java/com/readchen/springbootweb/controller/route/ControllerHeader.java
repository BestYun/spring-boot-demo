package com.readchen.springbootweb.controller.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ControllerHeader {

    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping("route/request")
    String getHeader(){

        return httpServletRequest.getHeader("Host");
    }


    @GetMapping("route/request/params")
    String getParams(){
        Map<String,String[]> params = httpServletRequest.getParameterMap();
        String result = "";
        for (String key:params.keySet()
             ) {
            result += "key = "+key+"\n";
        }

        return result;
    }


}
