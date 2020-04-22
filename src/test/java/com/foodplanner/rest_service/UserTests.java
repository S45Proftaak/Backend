package com.foodplanner.rest_service;

import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.ldap.Person;
import com.foodplanner.rest_service.ldap.PersonRepository;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserTests {

    private PersonRepository repository;

    public UserTests()
    {
        repository = new PersonRepository();
    }

    @Test
    void contextLoads() {
        List<Person> founduser = repository.findByEmail("testemail3@gmail.com");
        assertEquals(founduser.get(0).getFullName(), "Jahn Dae");
    }
}
