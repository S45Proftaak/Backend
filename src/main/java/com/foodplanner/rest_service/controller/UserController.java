package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.LoginDTO;
import com.foodplanner.rest_service.ldap.Person;
import com.foodplanner.rest_service.ldap.PersonRepository;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value = "getUserByID")
    @ResponseBody
    public User getUser(@RequestParam int id){
        return userRepository.findById(id).get();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO dto) {

        // Check email/password combination
        // If True do shit
        if(ldapRepository.authenticateByEmail(dto.getEmail(), dto.getPassword())) {
            List<Person> ps = ldapRepository.findByEmail(dto.getEmail());
            Person p = ps.get(0);
            User u = userRepository.findByEmail(dto.getEmail());
                if (u != null) {
                    jwtTokenProvider.createToken(u.getId(), u.getName(), u.getRole().getName());
                    return new ResponseEntity<>(HttpStatus.OK); // return with token
                } else {
                    User user = new User();
                    user.setEmail(dto.getEmail());
                    user.setName(p.getFullName());
                    user.setRole(roleRepository.findByName("Employee"));
                    userRepository.save(user);
                    jwtTokenProvider.createToken(user.getId(), user.getName(), user.getRole().getName());
                    return new ResponseEntity<>(HttpStatus.OK); // return with token
                }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}