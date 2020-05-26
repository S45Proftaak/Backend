package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.dtos.response.Scoreboard.OwnScoreBoardResponse;
import com.foodplanner.rest_service.dtos.response.Scoreboard.ScoreboardResponse;
import com.foodplanner.rest_service.endpoints.ScoreBoardEndpoint;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.ScoreBoardRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import com.foodplanner.rest_service.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
        Collections.sort(response, Collections.reverseOrder());
        return new ResponseEntity<>(response.stream().limit(3), HttpStatus.OK);
    }

    @GetMapping(ScoreBoardEndpoint.GET_SCOREBOARD_IN_TIME)
    public ResponseEntity<?> getScoreboardInTime(){
        List<ScoreboardResponse> response = new ArrayList<>();
        for(Scoreboard scoreboard : scoreBoardRepository.getTopThreeInTime()){
            response.add(new ScoreboardResponse(scoreboard.getUser(), scoreboard.getPoints_in_time()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(ScoreBoardEndpoint.GET_SCOREBOARD_TOO_LATE)
    public ResponseEntity<?> getScoreboardTooLate(){
        List<ScoreboardResponse> response = new ArrayList<>();
        for(Scoreboard scoreboard : scoreBoardRepository.getTopThreeTooLate()){
            response.add(new ScoreboardResponse(scoreboard.getUser(), scoreboard.getPoints_too_late()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(ScoreBoardEndpoint.GET_OWN_SCORES)
    public ResponseEntity<?> getOwnScores(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      Scoreboard scoreboard = scoreBoardRepository.findByUser(userRepository.findById(userDetails.getUser_id()).get());
      List<Scoreboard> inTimeByOrder = scoreBoardRepository.getAllScoreboardsInTimeByOrder();
      List<Scoreboard> tooLateByOrder = scoreBoardRepository.getAllScoreboardsTooLateOrder();

      OwnScoreBoardResponse ownScoreBoardResponse = new OwnScoreBoardResponse(scoreboard.getUser().getName(), scoreboard.getUser().getEmail(), scoreboard.getUser().getRole().getName(), scoreboard.getPoints_in_time(),
             inTimeByOrder.indexOf(scoreboard) + 1, scoreboard.getPoints_too_late(), tooLateByOrder.indexOf(scoreboard) + 1, positionInScoreboard(scoreboard));


      return new ResponseEntity<>(ownScoreBoardResponse, HttpStatus.OK);
    }

    private Integer positionInScoreboard(Scoreboard scoreboard){
        List<ScoreboardResponse> allByOrder = new ArrayList<>();
        ScoreboardResponse selectedScoreboard = new ScoreboardResponse(scoreboard.getUser(), (scoreboard.getPoints_in_time() + scoreboard.getPoints_too_late()));
        for(Scoreboard sc : scoreBoardRepository.findAll()){
            allByOrder.add(new ScoreboardResponse(sc.getUser(), (sc.getPoints_in_time() + sc.getPoints_too_late())));
        }
        Collections.sort(allByOrder, Collections.reverseOrder());
        return allByOrder.indexOf(selectedScoreboard) + 1;
    }
}
