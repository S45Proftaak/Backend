package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class UserController {

    @Autowired
    private UserRepository repo;

    @GetMapping(value = "getUserByID")
    @ResponseBody
    public User getUser(@RequestParam int id){
        return repo.findById(id).get();
    }
}
