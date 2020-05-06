package com.foodplanner.rest_service.controller;

import static org.assertj.core.api.Assertions.assertThat;
import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.NewOrderDTO;
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
import java.util.Optional;

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

    @Mock
    JwtTokenProvider jwtTokenProvider;



    @Test
    public void getFoodOrder_Failed() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        NewOrderDTO dto = new NewOrderDTO();
        dto.setDate(Date.valueOf("2015-03-31"));

        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(new FoodOrder());

        ResponseEntity<?> responseEntity = foodOrderController.addNewFoodOrder(request, dto);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void getFoodOrder_Accepted() {
        String bearerToken = provider.createToken(2, "Jihn Die", "Employee");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + bearerToken);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User user = new User();
        user.setName("Jihn Die");
        user.setId(2);

        NewOrderDTO dto = new NewOrderDTO();
        dto.setDate(Date.valueOf("2015-03-31"));

        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(new FoodOrder());
        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(bearerToken);
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));

        ResponseEntity<?> responseEntity = foodOrderController.addNewFoodOrder(request, dto);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }
}