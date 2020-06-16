package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Role;
import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.repositories.ScoreBoardRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import com.foodplanner.rest_service.services.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ScoreboardControllerTest {

    @InjectMocks
    ScoreBoardController scoreBoardController;

    @Mock
    ScoreBoardRepository scoreBoardRepository;

    @Mock
    UserRepository userRepository;

    @Test
    public void getScoreboardMostEaten_Happy(){
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));

        List<Scoreboard> scoreboards = new ArrayList<>();
        scoreboards.add(new Scoreboard(10L, 0L, user));
        scoreboards.add(new Scoreboard(20L, 0L, user));
        scoreboards.add(new Scoreboard(30L, 0L, user));

        when(scoreBoardRepository.findAll()).thenReturn(scoreboards);

        ResponseEntity<?> responseEntity = scoreBoardController.getScoreboardMostEaten();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getScoreboardInTime_Happy(){
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));

        List<Scoreboard> scoreboards = new ArrayList<>();
        scoreboards.add(new Scoreboard(10L, 0L, user));
        scoreboards.add(new Scoreboard(20L, 0L, user));
        scoreboards.add(new Scoreboard(30L, 0L, user));

        when(scoreBoardRepository.getTopThreeInTime()).thenReturn(scoreboards);

        ResponseEntity<?> responseEntity = scoreBoardController.getScoreboardMostEaten();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getScoreboardTooLate_Happy(){
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));

        List<Scoreboard> scoreboards = new ArrayList<>();
        scoreboards.add(new Scoreboard(10L, 0L, user));
        scoreboards.add(new Scoreboard(20L, 0L, user));
        scoreboards.add(new Scoreboard(30L, 0L, user));

        when(scoreBoardRepository.getTopThreeTooLate()).thenReturn(scoreboards);

        ResponseEntity<?> responseEntity = scoreBoardController.getScoreboardMostEaten();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getScoreboardOwnScores_Happy(){
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));

        List<Scoreboard> scoreboards = new ArrayList<>();
        scoreboards.add(new Scoreboard(10L, 0L, user));
        scoreboards.add(new Scoreboard(20L, 0L, user));
        scoreboards.add(new Scoreboard(30L, 0L, user));

        for(Scoreboard scoreboard : scoreboards){
            if(user.getId() == scoreboard.getUser().getId()){
                when(scoreBoardRepository.findByUser(any(User.class))).thenReturn(scoreboard);
            }
        }
        /**************************************** Authentication ****************************************************/
        UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
        UserDetails userdetails = userDetailsService.mockUser(user);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userdetails, null, userdetails.getAuthorities());
        /***********************************************************************************************************/

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(scoreBoardRepository.getTopThreeInTime()).thenReturn(scoreboards);
        when(scoreBoardRepository.getTopThreeTooLate()).thenReturn(scoreboards);

        ResponseEntity<?> responseEntity = scoreBoardController.getOwnScores(authentication);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
