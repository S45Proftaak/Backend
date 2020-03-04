package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.Meal;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;


@RestController("foodOrderController")
@CrossOrigin
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository repo;

    @GetMapping(value = "getFoodOrderByID")
    @ResponseBody
    public FoodOrder getFoodOrderByID (@RequestParam int id){
        return repo.findById(id).get();
    }

    @GetMapping(value = "addNewFoodOrder")
    @ResponseBody
    public void addNewFoodOrder (@RequestParam User user, Meal meal, Date date, byte toLate){

        FoodOrder newFoodOrder = new FoodOrder();
        newFoodOrder.setUserid(user);
        newFoodOrder.setMeal(meal);
        newFoodOrder.setDate(date);
        newFoodOrder.setToLate(toLate);
        repo.save(newFoodOrder);
    }
}
