package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.endpoints.ScoreBoardEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/scoreboard")
public class ScoreBoardController {

    @RequestMapping(ScoreBoardEndpoint.GET_ALL)
    public void test(){

    }
}
