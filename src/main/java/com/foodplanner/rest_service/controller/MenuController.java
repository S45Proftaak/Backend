package com.foodplanner.rest_service.controller;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;


@RestController("menuController")
@CrossOrigin
public class MenuController {

    @Autowired
    private MenuController repo;

    @GetMapping(value = "getID")
    @ResponseBody
    public String getid(@RequestParam String id){
        return null;

    }
}
