package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.Role;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SecretaryControllerTest {

    @InjectMocks
    SecretaryController secretaryController;

    @Mock
    FoodOrderRepository foodOrderRepository;

    @Test
    public void getUsersByDate_Happy(){
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);

        List<FoodOrder> foodOrderList = new ArrayList<>();
        foodOrderList.add(new FoodOrder(1, user, date, true));
        foodOrderList.add(new FoodOrder(2, user, date, true));
        foodOrderList.add(new FoodOrder(3, user, date, true));

        when(foodOrderRepository.findAllByDate(any(Date.class))).thenReturn(foodOrderList);

        ResponseEntity<?> responseEntity = secretaryController.getUsersByDate(date);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getUsersBetweenDates_Happy(){
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);

        List<FoodOrder> foodOrderList = new ArrayList<>();
        foodOrderList.add(new FoodOrder(1, user, date, true));
        foodOrderList.add(new FoodOrder(2, user, date, true));
        foodOrderList.add(new FoodOrder(3, user, date, true));

        when(foodOrderRepository.findAllByDateBetween(any(Date.class), any(Date.class))).thenReturn(foodOrderList);

        ResponseEntity<?> responseEntity = secretaryController.getUsersBetweenDates(date, date);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
