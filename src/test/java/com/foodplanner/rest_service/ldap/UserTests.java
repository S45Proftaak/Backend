package com.foodplanner.rest_service.ldap;

import com.foodplanner.rest_service.ldap.Person;
import com.foodplanner.rest_service.ldap.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserTests {

    @Autowired
    private PersonRepository repository;

    @Test
    void findUserByEmailTest() {
        List<Person> founduser = repository.findByEmail("rico@test.nl");
        assertEquals(founduser.get(0).getFullName(), "Rico Muijtjens");
    }

    @Test
    void notFoundUserEmailTest() {
        List<Person> foundPerson = repository.findByEmail("randomnotexistinguser@gmail.com");
        assertEquals(0, foundPerson.size());
        assertTrue(foundPerson.isEmpty());
    }

    @Test
    void findUserByNameTest() {
        List<Person> founduser = repository.findByName("Rico Muijtjens");
        assertEquals("rico@test.nl", founduser.get(0).getEmail());
    }

    @Test
    void notFoundUserNameTest() {
        List<Person> foundPerson = repository.findByName("NON Existing");
        assertEquals(0, foundPerson.size());
        assertTrue(foundPerson.isEmpty());
    }

    @Test
    void authenticateEmail()
    {
        assertTrue(repository.authenticateByEmail("rico@test.nl", "secret"));
    }
}
