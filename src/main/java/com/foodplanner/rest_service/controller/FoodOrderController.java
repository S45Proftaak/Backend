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

    @GetMapping(value = "getFoodOrdersByID")
    @ResponseBody
    public List<FoodOrder> getFoodOrdersByID(HttpServletRequest req){
        String token = tokenProvider.resolveToken(req);

        User user = userRepository.findById(tokenProvider.getUserIdFromToken(token)).get();
        List<FoodOrder> allOrders = (List<FoodOrder>) foodOrderRepository.findAll();
        ArrayList<FoodOrder> userOrders = new ArrayList<>();
        for(FoodOrder order : allOrders){
            if(order.getUser() == user){
                userOrders.add(order);
            }
        }

        return userOrders;
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
        foodOrderRepository.save(newOrder);

    }
}
