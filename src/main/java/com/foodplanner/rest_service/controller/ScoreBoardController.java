package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.dtos.response.Scoreboard.ScoreResponse;
import com.foodplanner.rest_service.dtos.response.Scoreboard.ScoreboardResponse;
import com.foodplanner.rest_service.endpoints.ScoreBoardEndpoint;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.ScoreBoardRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        List<ScoreboardResponse> response = new ArrayList<>();
        for(Scoreboard scoreboard : scoreBoardRepository.findAll()){
            response.add(new ScoreboardResponse(scoreboard.getUser(), (scoreboard.getPoints_in_time() + scoreboard.getPoints_too_late())));
        }
        Link self = linkTo(methodOn(this.getClass()).getScoreboardMostEaten()).withSelfRel();
        //TODO: Making sure the link shows up in the response.
        Collections.sort(response, Collections.reverseOrder());
        return new ResponseEntity<>(response.stream().limit(20), HttpStatus.OK);
    }

    @GetMapping(ScoreBoardEndpoint.GET_SCOREBOARD_IN_TIME)
    public ResponseEntity<?> getScoreboardInTime(){
        List<ScoreboardResponse> response = new ArrayList<>();
        for(Scoreboard scoreboard : scoreBoardRepository.getTopTwentyInTime()){
            response.add(new ScoreboardResponse(scoreboard.getUser(), scoreboard.getPoints_in_time()));
        }
        return new ResponseEntity<>(new ScoreResponse(response).add(linkTo(methodOn(this.getClass()).getScoreboardInTime()).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping(ScoreBoardEndpoint.GET_SCOREBOARD_TOO_LATE)
    public ResponseEntity<?> getScoreboardTooLate(){
        List<ScoreboardResponse> response = new ArrayList<>();
        for(Scoreboard scoreboard : scoreBoardRepository.getTopTwentyTooLate()){
            response.add(new ScoreboardResponse(scoreboard.getUser(), scoreboard.getPoints_too_late()));
        }
        return new ResponseEntity<>(new ScoreResponse(response).add(linkTo(methodOn(this.getClass()).getScoreboardTooLate()).withSelfRel()), HttpStatus.OK);
    }

}
