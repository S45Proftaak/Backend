package com.foodplanner.rest_service.controller;

import static org.assertj.core.api.Assertions.assertThat;
import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.ldap.PersonRepository;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MealEndpoint {

    @InjectMocks
    FoodOrderController foodOrderController;

    @InjectMocks
    JwtTokenProvider provider;

    @Mock
    FoodOrderRepository foodOrderRepository;

    @Mock
    UserRepository userRepository;



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
        String bearerToken = provider.createToken(2, "Jihn Die", "Employee");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + bearerToken);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String str = "2015-03-31";
        Date date = Date.valueOf(str);

        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(new FoodOrder());
        when(provider.resolveToken(any(HttpServletRequest.class))).thenReturn(bearerToken);

        ResponseEntity<Object> responseEntity = foodOrderController.addNewFoodOrder(request, date);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }
}