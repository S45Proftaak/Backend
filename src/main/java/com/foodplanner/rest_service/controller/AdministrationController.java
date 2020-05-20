package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.configuration.PriceConfiguration;
import com.foodplanner.rest_service.configuration.WritePropperties;
import com.foodplanner.rest_service.databasemodel.Role;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.administration.request.UpdatePriceDTO;
import com.foodplanner.rest_service.dtos.administration.request.UpdateUserRoleDTO;
import com.foodplanner.rest_service.dtos.administration.response.RolesDTO;
import com.foodplanner.rest_service.dtos.administration.response.UsersDTO;
import com.foodplanner.rest_service.endpoints.AdministrationEndpoints;
import com.foodplanner.rest_service.exceptions.FileNotWritable;
import com.foodplanner.rest_service.repositories.RoleRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = AdministrationEndpoints.BASE)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
public class AdministrationController {

    @Autowired
    private PriceConfiguration priceConfiguration;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PutMapping(value = AdministrationEndpoints.UPDATE_PRICE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePrice(@RequestBody UpdatePriceDTO priceDTO) throws FileNotWritable {
        priceConfiguration.setPrice(priceDTO.getPrice());
        WritePropperties.writePropsToFile("configuration.price", priceDTO.getPrice(), "settings.properties");
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = AdministrationEndpoints.GET_ALL_USERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(){
        Iterable<User> users = userRepository.findAll();

        //region User List Conversion
        List<User> userList = new ArrayList<>();
        for (User u: users) {
            userList.add(u);
        }
        //endregion

        UsersDTO dto = new UsersDTO(userList);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PutMapping(value = AdministrationEndpoints.UPDATE_USER_ROLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserRole(@RequestBody UpdateUserRoleDTO dto){
        User user = userRepository.findById(dto.getUserID()).get();
        Role role = roleRepository.findById(dto.getRoleID()).get();
        user.setRole(role);
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = AdministrationEndpoints.GET_ROLES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoles(){
        Iterable<Role> roles = roleRepository.findAll();
        RolesDTO dto = new RolesDTO(roles);
        return new ResponseEntity(roles, HttpStatus.OK);
    }


}
