package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.LoginDTO;
import com.foodplanner.rest_service.dtos.LoginReturnDTO;
import com.foodplanner.rest_service.endpoints.AuthEndpoint;
import com.foodplanner.rest_service.ldap.Person;
import com.foodplanner.rest_service.ldap.PersonRepository;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.RoleRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

    User user = new User();

    @PostMapping(value = AuthEndpoint.LOGIN)
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO dto) {
        LoginReturnDTO returnDTO = new LoginReturnDTO();
        if(ldapRepository.authenticateByEmail(dto.getEmail(), dto.getPassword())) {
            List<Person> ps = ldapRepository.findByEmail(dto.getEmail());
            Person p = ps.get(0);
            User dbUser = userRepository.findByEmail(dto.getEmail());
                if (dbUser != null) {
                    returnDTO.setToken(jwtTokenProvider.createToken(dbUser.getId(), dbUser.getName(), dbUser.getRole().getName(), dto.getEmail()));
                    return new ResponseEntity<>(returnDTO, HttpStatus.OK);
                } else {
                    setNewUser(dto.getEmail(), p.getFullName(), "ROLE_EMPLOYEE");
                    userRepository.save(user);
                    returnDTO.setToken(jwtTokenProvider.createToken(user.getId(), user.getName(), user.getRole().getName(), dto.getEmail()));
                    return new ResponseEntity<>(returnDTO, HttpStatus.OK); // return with token
                }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public void setNewUser(String email, String name, String role){
        user.setEmail(email);
        user.setName(name);
        user.setRole(roleRepository.findByName(role));
        user.setScoreboard(new Scoreboard(0L, 0L, user));
    }
}