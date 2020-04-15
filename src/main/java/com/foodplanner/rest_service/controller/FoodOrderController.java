package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.WeekDTO;
import com.foodplanner.rest_service.logic.foodorder.DateChecker;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController()
@RequestMapping(value = "foodorder")
@CrossOrigin
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/all-orders-by-id")
    @ResponseBody
    public List<FoodOrder> getFoodOrdersByUserID(HttpServletRequest req){
        String token = tokenProvider.resolveToken(req);

        User user = userRepository.findById(tokenProvider.getUserIdFromToken(token)).get();
        return foodOrderRepository.findAllByUser(user);
    }

    @GetMapping(value = "/all-orders-per-week")
    @ResponseBody
    public List<Date> getFoodOrdersPerWeek(HttpServletRequest req, @RequestBody WeekDTO weekDTO){
        String token = tokenProvider.resolveToken(req);
        DateChecker checker = new DateChecker();

        return checker.checkDates(foodOrderRepository.findAllByUser(userRepository.findById(tokenProvider.getUserIdFromToken(token)).get()),
                weekDTO.getDates());
    }

    @PostMapping(value = "/add-order")
    @ResponseBody
    public void addNewFoodOrder (HttpServletRequest req, @RequestParam(value = "date") Date date){

        String token = tokenProvider.resolveToken(req);

        User user = userRepository.findById(tokenProvider.getUserIdFromToken(token)).get();
        FoodOrder newOrder = new FoodOrder();
        newOrder.setUser(user);
        newOrder.setDate(date);
        foodOrderRepository.save(newOrder);
    }
}
