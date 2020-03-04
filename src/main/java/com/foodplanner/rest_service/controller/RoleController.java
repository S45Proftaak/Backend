package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Role;
import com.foodplanner.rest_service.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("roleController")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleRepository repo;

    @GetMapping(value = "addNewRole")
    @ResponseBody
    public void addNewRole (@RequestParam String name){

        Role newRole = new Role();
        newRole.setName(name);
        repo.save(newRole);
    }

    @GetMapping(value = "getRoleByID")
    @ResponseBody
    public Role getRoleByID (@RequestParam int id){
        return repo.findById(id).get();
    }
}
