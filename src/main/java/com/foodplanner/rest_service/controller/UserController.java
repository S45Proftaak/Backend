package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Role;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping(value = "getUserByID")
    @ResponseBody
    public User getUser(@RequestParam int id){
        return userRepository.findById(id).get();
    }

    @GetMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestParam(value = "email")String email, @RequestParam(value = "password")String password){

        // Check user in LDAP Server first

        for(User u : userRepository.findAll()){
            if(u.getEmail().equals(email)){
                jwtTokenProvider.createToken(u.getId(), u.getName(), u.getRole().getName());
                return new ResponseEntity<>(HttpStatus.OK); // return with token
            }else{
               User user = new User();
               user.setEmail(email);
               user.setName("LDAP NAME");
               // Set user Role
                jwtTokenProvider.createToken(user.getId(), user.getName(), user.getRole().getName());
                return new ResponseEntity<>(HttpStatus.OK); // return with token
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}