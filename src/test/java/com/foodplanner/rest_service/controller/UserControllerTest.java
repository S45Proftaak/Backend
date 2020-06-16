package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Role;
import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.LoginDTO;
import com.foodplanner.rest_service.ldap.Person;
import com.foodplanner.rest_service.ldap.PersonRepository;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.RoleRepository;
import com.foodplanner.rest_service.repositories.ScoreBoardRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.LdapTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    PersonRepository ldapRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    ScoreBoardRepository scoreBoardRepository;

    @Test
    public void loginUser_Happy(){
        Person person = new Person();
        person.setEmail("test@test.nl");
        person.setPassword("secret");

        List<Person> personList = new ArrayList<>();
        personList.add(person);

        User user = new User();
        user.setEmail("test@test.nl");
        user.setId(1);
        user.setName("Test");
        user.setRole(new Role(1, "ROLE_EMPLOYEE"));

        Scoreboard scoreboard = new Scoreboard(0L, 0L, user);

        JwtTokenProvider provider = new JwtTokenProvider();

        LoginDTO dto = new LoginDTO();
        dto.setEmail("test@test.nl");
        dto.setPassword("secret");


        when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(scoreBoardRepository.save(any(Scoreboard.class))).thenReturn(scoreboard);
        when(jwtTokenProvider.createToken(any(Integer.class), any(String.class), any(String.class), any(String.class))).thenReturn(provider.createToken(user.getId(), user.getName(), user.getRole().getName(), user.getEmail()));
        when(roleRepository.findByName(any(String.class))).thenReturn(new Role(1, "ROLE_EMPLOYEE"));
        when(ldapRepository.findByEmail(any(String.class))).thenReturn(personList);
        when(ldapRepository.authenticateByEmail(any(String.class), any(String.class))).thenReturn(true);

        ResponseEntity<?> responseEntity = userController.loginUser(dto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void loginUser_Sad(){
        Person person = new Person();
        person.setEmail("test@test.nl");
        person.setPassword("secret");

        List<Person> personList = new ArrayList<>();
        personList.add(person);

        User user = new User();
        user.setEmail("test@test.nl");
        user.setId(1);
        user.setName("Test");
        user.setRole(new Role(1, "ROLE_EMPLOYEE"));

        Scoreboard scoreboard = new Scoreboard(0L, 0L, user);

        JwtTokenProvider provider = new JwtTokenProvider();

        LoginDTO dto = new LoginDTO();
        dto.setEmail("test@test.nl");


        when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(scoreBoardRepository.save(any(Scoreboard.class))).thenReturn(scoreboard);
        when(jwtTokenProvider.createToken(any(Integer.class), any(String.class), any(String.class), any(String.class))).thenReturn(provider.createToken(user.getId(), user.getName(), user.getRole().getName(), user.getEmail()));
        when(roleRepository.findByName(any(String.class))).thenReturn(new Role(1, "ROLE_EMPLOYEE"));
        when(ldapRepository.findByEmail(any(String.class))).thenReturn(personList);
        when(ldapRepository.authenticateByEmail(any(String.class), any(String.class))).thenReturn(true);

        ResponseEntity<?> responseEntity = userController.loginUser(dto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
