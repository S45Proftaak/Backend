package com.foodplanner.rest_service.ldap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ldap")
@CrossOrigin
public class LdapController {

    @Autowired
    PersonRepository repository;

    @GetMapping("/test")
    public String test()
    {
        Person p = repository.findOne("jahn");
        if(p.getPassword().equals("secret"))
        {
            return "true";
        }
        return "false";
    }
}
