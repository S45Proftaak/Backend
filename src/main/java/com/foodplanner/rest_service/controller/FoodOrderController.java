package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;


@RestController("foodOrderController")
@CrossOrigin
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository repo;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "getFoodOrderByID")
    @ResponseBody
    public FoodOrder getFoodOrderByID (@RequestParam int id){
        return repo.findById(id).get();
    }

    @PostMapping(value = "addNewFoodOrder")
    @ResponseBody
    public void addNewFoodOrder (HttpServletRequest req){

        String token = tokenProvider.resolveToken(req);

        User user = userRepository.findById(tokenProvider.getUserIdFromToken(token)).get();
        FoodOrder newOrder = new FoodOrder();
        newOrder.setUser(user);
        Date date = java.sql.Date.valueOf(java.time.LocalDate.now());
        newOrder.setDate(date);
        repo.save(newOrder);

    }
}
