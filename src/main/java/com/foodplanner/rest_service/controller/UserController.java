package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.ldap.Person;
import com.foodplanner.rest_service.ldap.PersonRepository;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PersonRepository ldapRepository;

    @GetMapping(value = "getUserByID")
    @ResponseBody
    public User getUser(@RequestParam int id){
        return userRepository.findById(id).get();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestParam(value = "email")String email, @RequestParam(value = "password")String password){

        // Check email/password combination
        // If True do shit
        if(ldapRepository.authenticateByEmail(email, password)) {
            List<Person> ps = ldapRepository.findByEmail(email);
            Person p = ps.get(0);
            User u = userRepository.findByEmail(email);
                if (u != null) {
                    jwtTokenProvider.createToken(u.getId(), u.getName(), u.getRole().getName());
                    return new ResponseEntity<>(HttpStatus.OK); // return with token
                } else {
                    User user = new User();
                    user.setEmail(email);
                    user.setName(p.getFullName());
                    userRepository.save(user);
                    jwtTokenProvider.createToken(user.getId(), user.getName(), user.getRole().getName());
                    return new ResponseEntity<>(HttpStatus.OK); // return with token
                }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}