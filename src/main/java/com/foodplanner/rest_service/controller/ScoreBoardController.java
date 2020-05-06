package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.endpoints.ScoreBoardEndpoint;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.ScoreBoardRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/scoreboard")
public class ScoreBoardController {

    @Autowired
    ScoreBoardRepository scoreBoardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @GetMapping(ScoreBoardEndpoint.GET_SCOREBOARD)
    public Scoreboard getScoreboard(HttpServletRequest request){
        String token = tokenProvider.resolveToken(request);
        return scoreBoardRepository.getByUser(userRepository.findById(tokenProvider.getUserIdFromToken(token)).get());
    }
}
