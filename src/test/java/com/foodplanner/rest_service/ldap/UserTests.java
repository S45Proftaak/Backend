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
        List<Person> founduser = repository.findByEmail("testemail3@gmail.com");
        assertEquals(founduser.get(0).getFullName(), "Jahn Dae");
    }

    @Test
    void notFoundUserEmailTest() {
        List<Person> foundPerson = repository.findByEmail("randomnotexistinguser@gmail.com");
        assertEquals(0, foundPerson.size());
        assertTrue(foundPerson.isEmpty());
    }

    @Test
    void findUserByNameTest() {
        List<Person> founduser = repository.findByName("Jahn Dae");
        assertEquals("testemail3@gmail.com", founduser.get(0).getEmail());
    }

    @Test
    void notFoundUserNameTest() {
        List<Person> foundPerson = repository.findByName("NON Existing");
        assertEquals(0, foundPerson.size());
        assertTrue(foundPerson.isEmpty());
    }

    @Test
    void getRoleFromUser() {
        List<Person> founduser = repository.findByName("Jahn Dae");
        assertEquals("Secretary", founduser.get(0).getRole());
    }

    @Test
    void authenticateEmail()
    {
        assertTrue(repository.authenticateByEmail("testemail3@gmail.com", "secret"));
    }

    @Test
    void findAllPeopleCount() {
        List<Person> allUsers = repository.findAll();
        assertEquals(3, allUsers.size());
    }

    @Test
    void createNewUserTestAll() {
        Person person = new Person("Test User", "User", "Password", "unittestmail@gmail.com", "Admin");
        assertEquals(0, repository.findByName("Test User").size());
        assertTrue(repository.findByName("Test User").isEmpty());
        repository.create(person);
        assertEquals(1, repository.findByName("Test User").size());
        repository.delete(person);
        assertEquals(0, repository.findByName("Test User").size());
        assertTrue(repository.findByName("Test User").isEmpty());
    }
}
