package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.dtos.UserByDateDTO;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.mappings.SecretaryEndpoint;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping(value = SecretaryEndpoint.BASE)
public class SecretaryController {

    @Autowired
    private JwtTokenProvider jwtProvider;
    @Autowired
    private FoodOrderRepository foodOrderRepository;

    private static final String ALLOWEDROLE = "Secretary";

    @GetMapping(value = SecretaryEndpoint.GET_USERS_BY_DATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersByDate(@RequestParam Date date, HttpServletRequest request){
        String token = jwtProvider.resolveToken(request);
        String role = jwtProvider.getRoleFromToken(token);
        if(!role.equals(ALLOWEDROLE)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<FoodOrder> orders = foodOrderRepository.findAllByDate(date);
        List<UserByDateDTO> dto = new ArrayList<>();
        for (FoodOrder order: orders) {
            dto.add(new UserByDateDTO(order.getUser().getName(), order.getDate(), order.getToLate()));
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = SecretaryEndpoint.GET_USERS_BETWEEN_DATES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersBetweenDates(@RequestParam Date start, @RequestParam Date end, HttpServletRequest request){
        String token = jwtProvider.resolveToken(request);
        String role = jwtProvider.getRoleFromToken(token);
        if(!role.equals(ALLOWEDROLE)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<FoodOrder> orders = foodOrderRepository.findAllByDateBetween(start, end);
        List<UserByDateDTO> dto = new ArrayList<>();
        for (FoodOrder order: orders){
            dto.add(new UserByDateDTO(order.getUser().getName(), order.getDate(),order.getToLate()));
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
