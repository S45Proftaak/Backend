package com.foodplanner.rest_service.controller;

import static org.assertj.core.api.Assertions.assertThat;
import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.dtos.LoginDTO;
import com.foodplanner.rest_service.ldap.PersonRepository;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.naming.ldap.LdapName;
import java.sql.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@ActiveProfiles("test")
public class MealEndpoint {

    @InjectMocks
    FoodOrderController foodOrderController;

    @InjectMocks
    UserController userController;

    @Mock
    FoodOrderRepository foodOrderRepository;

    @Mock
    PersonRepository repository;

    @Test
    public void getFoodOrder_Failed() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String str = "2015-03-31";
        Date date = Date.valueOf(str);

        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(new FoodOrder());

        ResponseEntity<Object> responseEntity = foodOrderController.addNewFoodOrder(request, date);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void getFoodOrder_Accepted() {
        ResponseEntity<?> loginEntity = userController.loginUser(new LoginDTO("testemail2@gmail.com", "secret"));


        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization:", loginEntity.getBody());
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String str = "2015-03-31";
        Date date = Date.valueOf(str);

        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(new FoodOrder());

        ResponseEntity<Object> responseEntity = foodOrderController.addNewFoodOrder(request, date);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
    }
}
