package com.readchen.springbootlog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LogController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/")
    String index(){
//        Logger.
        logger.debug("url / index");

        return "index";
    }

}
