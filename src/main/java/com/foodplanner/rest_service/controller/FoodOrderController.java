package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.WeekDTO;
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


@RestController("foodOrderController")
@CrossOrigin
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "getFoodOrdersByUserID")
    @ResponseBody
    public List<FoodOrder> getFoodOrdersByUserID(HttpServletRequest req){
        String token = tokenProvider.resolveToken(req);

        User user = userRepository.findById(tokenProvider.getUserIdFromToken(token)).get();
        return foodOrderRepository.findAllByUser(user);
    }

    @GetMapping(value = "getOrdersPerWeek")
    @ResponseBody
    public List<Date> getFoodOrdersPerWeek(HttpServletRequest req, @RequestBody WeekDTO weekDTO){
        String token = tokenProvider.resolveToken(req);

        List<Date> foundDates = new ArrayList<>();

        for (FoodOrder order : foodOrderRepository.findAllByUser(userRepository.findById(tokenProvider.getUserIdFromToken(token)).get())){
            for (Date date : weekDTO.getDates()){
                if(date.equals(order.getDate())){
                    foundDates.add(date);
                }
            }
        }
        return foundDates;
    }

    @PostMapping(value = "addNewFoodOrder")
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
