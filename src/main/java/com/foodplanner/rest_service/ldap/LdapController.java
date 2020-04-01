package com.foodplanner.rest_service.ldap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ldap")
@CrossOrigin
public class LdapController {

    @Autowired
    PersonRepository repository;

    @GetMapping("/test")
    public boolean test()
    {
        List<Person> p = repository.findByEmail("testemail3@gmail.com");
        System.out.println(p);
        boolean correct = repository.authenticateByEmail("testemail3@gmail.com", "secret");
        return correct;
    }
}
