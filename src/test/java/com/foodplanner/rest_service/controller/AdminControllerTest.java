package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.Role;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.administration.request.UpdateUserRoleDTO;
import com.foodplanner.rest_service.repositories.RoleRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AdminControllerTest {

    @InjectMocks
    AdminController adminController;

    @Mock
    RoleRepository roleRepository;

    @Mock
    UserRepository userRepository;

    @Test
    public void getAllUsers_Happy_Flow(){
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Test Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE")));
        users.add(new User(1, "Test Test", "test@test.nl", new Role(1, "ROLE_ADMIN")));

        when(userRepository.findAll()).thenReturn(users);

        ResponseEntity<?> responseEntity = adminController.getAllUsers();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void updateUser_Happy_Flow(){
        User user = new User(1, "Test Test", "test@test.nl", new Role(1, "ROLE_ADMIN"));

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(roleRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Role(2, "ROLE_ADMIN")));
        UpdateUserRoleDTO dto = new UpdateUserRoleDTO();

        dto.setRoleID(2);
        dto.setUserID(1);

        ResponseEntity<?> responseEntity = adminController.updateUserRole(dto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void updateUser_Sad_Flow(){
        User user = new User(1, "Test Test", "test@test.nl", new Role(1, "ROLE_ADMIN"));

        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1, "ROLE_ADMIN"));
        roles.add(new Role(2, "ROLE_EMPLOYEE"));

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));

        for(Role role : roles){
            if(role.getId() == 3){
                when(roleRepository.findById(any(Integer.class))).thenReturn(Optional.of(role));
            }
        }

        UpdateUserRoleDTO dto = new UpdateUserRoleDTO();
        dto.setRoleID(3);
        dto.setUserID(1);

        ResponseEntity<?> responseEntity = adminController.updateUserRole(dto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getRoles_Happy_Flow_Assert_StatusCode(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1, "ROLE_ADMIN"));
        roles.add(new Role(2, "ROLE_EMPLOYEE"));

        when(roleRepository.findAll()).thenReturn(roles);

        ResponseEntity<?> responseEntity = adminController.getRoles();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getRoles_Happy_Flow_Assert_RoleList(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1, "ROLE_ADMIN"));
        roles.add(new Role(2, "ROLE_EMPLOYEE"));

        when(roleRepository.findAll()).thenReturn(roles);

        ResponseEntity<?> responseEntity = adminController.getRoles();

        assertThat(responseEntity.getBody()).isEqualTo(roles);
    }
}