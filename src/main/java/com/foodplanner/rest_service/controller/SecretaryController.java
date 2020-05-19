package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.dtos.UserByDateDTO;
import com.foodplanner.rest_service.endpoints.SecretaryEndpoint;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import com.foodplanner.rest_service.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = SecretaryEndpoint.BASE)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_SECRETARY') or hasRole('ROLE_ADMIN')")
public class SecretaryController {

    @Autowired
    private FoodOrderRepository foodOrderRepository;


    @GetMapping(value = SecretaryEndpoint.GET_USERS_BY_DATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersByDate(@RequestParam Date date){
        List<FoodOrder> orders = foodOrderRepository.findAllByDate(date);
        List<UserByDateDTO> dto = new ArrayList<>();
        for (FoodOrder order: orders) {
            dto.add(new UserByDateDTO(order.getUser().getName(), order.getDate(), order.getToLate()));
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = SecretaryEndpoint.GET_USERS_BETWEEN_DATES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersBetweenDates(@RequestParam Date start, @RequestParam Date end){
        List<FoodOrder> orders = foodOrderRepository.findAllByDateBetween(start, end);
        List<UserByDateDTO> dto = new ArrayList<>();
        for (FoodOrder order: orders){
            dto.add(new UserByDateDTO(order.getUser().getName(), order.getDate(),order.getToLate()));
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getAuthorities().toString();
    }
}
