package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.dtos.UserByDateDTO;
import com.foodplanner.rest_service.dtos.secretary.response.GetUserByDateDTO;
import com.foodplanner.rest_service.endpoints.SecretaryEndpoint;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import com.foodplanner.rest_service.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        Link self = linkTo(methodOn(this.getClass()).getUsersByDate(date)).withSelfRel();
        return new ResponseEntity<>(new GetUserByDateDTO(dto).add(self), HttpStatus.OK);
    }

    @GetMapping(value = SecretaryEndpoint.GET_USERS_BETWEEN_DATES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersBetweenDates(@RequestParam Date start, @RequestParam Date end){
        List<FoodOrder> orders = foodOrderRepository.findAllByDateBetween(start, end);
        List<UserByDateDTO> dto = new ArrayList<>();
        for (FoodOrder order: orders){
            dto.add(new UserByDateDTO(order.getUser().getName(), order.getDate(),order.getToLate()));
        }
        Link self = linkTo(methodOn(this.getClass()).getUsersBetweenDates(start, end)).withSelfRel();
        return new ResponseEntity<>(new GetUserByDateDTO(dto).add(self), HttpStatus.OK);
    }

}
