package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.dtos.response.Scoreboard.ScoreboardMostEatenResponse;
import com.foodplanner.rest_service.endpoints.ScoreBoardEndpoint;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.ScoreBoardRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = ScoreBoardEndpoint.BASE)
public class ScoreBoardController {

    @Autowired
    ScoreBoardRepository scoreBoardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @GetMapping(ScoreBoardEndpoint.GET_SCOREBOARD_MOST_EATEN)
    public ResponseEntity<?> getScoreboardMostEaten(){
        List<ScoreboardMostEatenResponse> response = new ArrayList<>();
        for(Scoreboard scoreboard : scoreBoardRepository.findAll()){
            response.add(new ScoreboardMostEatenResponse(scoreboard.getUser(), (scoreboard.getPoints_in_time() + scoreboard.getPoints_too_late())));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
