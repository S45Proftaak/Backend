package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Meal;
import com.foodplanner.rest_service.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("mealController")
@CrossOrigin
public class MealController {

    @Autowired
    private MealRepository repo;

    @GetMapping(value = "addNewMeal")
    @ResponseBody
    public void addNewMeal (@RequestParam String name, int price){

        Meal newMeal = new Meal();
        newMeal.setName(name);
        newMeal.setPrice(price);
        repo.save(newMeal);
    }

    @GetMapping(value = "getMealByID")
    @ResponseBody
    public Meal getMealByID (@RequestParam int id){
        return repo.findById(id).get();
    }
}
