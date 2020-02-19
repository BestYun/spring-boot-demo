package com.readchen.springbootlog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LombokLogController {

    @GetMapping("log")
    String index(){
        log.info("LombokLogController");
        return "log index";
    }


}
