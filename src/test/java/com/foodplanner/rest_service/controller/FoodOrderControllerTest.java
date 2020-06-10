package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.configuration.PriceConfiguration;
import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.Role;
import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.NewOrderDTO;
import com.foodplanner.rest_service.logic.foodorder.DateChecker;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import com.foodplanner.rest_service.repositories.RoleRepository;
import com.foodplanner.rest_service.repositories.ScoreBoardRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FoodOrderControllerTest {

    @InjectMocks
    FoodOrderController foodOrderController;

    @Mock
    JwtTokenProvider tokenProvider;

    @Mock
    RoleRepository roleRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    FoodOrderRepository foodOrderRepository;

    @Mock
    DateChecker dateChecker;

    @Mock
    ScoreBoardRepository scoreBoardRepository;

    @Mock
    PriceConfiguration configuration;

    @Test
    public void getFoodOrdersById_Happy_AssertWithBody(){
        JwtTokenProvider provider = new JwtTokenProvider();
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));
        String token = provider.createToken(user.getId(),user.getName(), user.getRole().getName(), user.getEmail());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        List<FoodOrder> foodOrderList = new ArrayList<>();
        foodOrderList.add(new FoodOrder());
        foodOrderList.add(new FoodOrder());
        foodOrderList.add(new FoodOrder());

        when(tokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
        when(tokenProvider.getUserIdFromToken(any(String.class))).thenReturn(user.getId());
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(foodOrderRepository.findAllByUser(any(User.class))).thenReturn(foodOrderList);

        ResponseEntity<?> responseEntity = foodOrderController.getFoodOrdersByUserID(request);

        assertThat(responseEntity.getBody()).isEqualTo(foodOrderList);
    }

    @Test
    public void getFoodOrdersById_Happy_AssertWithResponseCode(){
        JwtTokenProvider provider = new JwtTokenProvider();
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));
        String token = provider.createToken(user.getId(),user.getName(), user.getRole().getName(), user.getEmail());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        List<FoodOrder> foodOrderList = new ArrayList<>();
        foodOrderList.add(new FoodOrder());
        foodOrderList.add(new FoodOrder());
        foodOrderList.add(new FoodOrder());

        when(tokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
        when(tokenProvider.getUserIdFromToken(any(String.class))).thenReturn(user.getId());
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(foodOrderRepository.findAllByUser(any(User.class))).thenReturn(foodOrderList);

        ResponseEntity<?> responseEntity = foodOrderController.getFoodOrdersByUserID(request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getOrdersPerWeek_Happy(){
        JwtTokenProvider provider = new JwtTokenProvider();
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));
        String token = provider.createToken(user.getId(),user.getName(), user.getRole().getName(), user.getEmail());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);

        List<FoodOrder> foodOrderList = new ArrayList<>();
        foodOrderList.add(new FoodOrder(1, user, date, true));
        foodOrderList.add(new FoodOrder(2, user, date, true));
        foodOrderList.add(new FoodOrder(3, user, date, true));

        List<String> dates = new ArrayList<>();
        dates.add("2020/06/10");
        dates.add("2020/06/11");
        dates.add("2020/06/12");

        when(tokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
        when(tokenProvider.getUserIdFromToken(any(String.class))).thenReturn(user.getId());
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(foodOrderRepository.findAllByUser(any(User.class))).thenReturn(foodOrderList);

        ResponseEntity<?> responseEntity = foodOrderController.getFoodOrdersPerWeek(dates, request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getOrdersPerWeek_Sad(){
        JwtTokenProvider provider = new JwtTokenProvider();
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));
        String token = provider.createToken(user.getId(),user.getName(), user.getRole().getName(), user.getEmail());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);

        List<FoodOrder> foodOrderList = new ArrayList<>();
        foodOrderList.add(new FoodOrder(1, user, date, true));
        foodOrderList.add(new FoodOrder(2, user, date, true));
        foodOrderList.add(new FoodOrder(3, user, date, true));

        List<String> dates = new ArrayList<>();
        for(int i = 0; i < 32; i++){
            dates.add("2020/06/10");
        }

        when(tokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
        when(tokenProvider.getUserIdFromToken(any(String.class))).thenReturn(user.getId());
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(foodOrderRepository.findAllByUser(any(User.class))).thenReturn(foodOrderList);

        ResponseEntity<?> responseEntity = foodOrderController.getFoodOrdersPerWeek(dates, request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getOrdersPerWeek_Sad_NoDatesInList(){
        JwtTokenProvider provider = new JwtTokenProvider();
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));
        String token = provider.createToken(user.getId(),user.getName(), user.getRole().getName(), user.getEmail());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);

        List<FoodOrder> foodOrderList = new ArrayList<>();
        foodOrderList.add(new FoodOrder(1, user, date, true));
        foodOrderList.add(new FoodOrder(2, user, date, true));
        foodOrderList.add(new FoodOrder(3, user, date, true));

        List<String> dates = new ArrayList<>();

        when(tokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
        when(tokenProvider.getUserIdFromToken(any(String.class))).thenReturn(user.getId());
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(foodOrderRepository.findAllByUser(any(User.class))).thenReturn(foodOrderList);

        ResponseEntity<?> responseEntity = foodOrderController.getFoodOrdersPerWeek(dates, request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void addOrder_Happy() {
        JwtTokenProvider provider = new JwtTokenProvider();
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));
        Scoreboard scoreboard = new Scoreboard(0L, 0L, user);

        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);

        FoodOrder foodOrder = new FoodOrder(1, user, date, true);
        String token = provider.createToken(user.getId(),user.getName(), user.getRole().getName(), user.getEmail());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        NewOrderDTO dto = new NewOrderDTO();
        dto.setDate(date);

        when(tokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(token);
        when(tokenProvider.getUserIdFromToken(any(String.class))).thenReturn(user.getId());
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(foodOrderRepository.findByUserAndDate(any(User.class), any(Date.class))).thenReturn(foodOrder);
        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(foodOrder);
        when(scoreBoardRepository.findByUser(any(User.class))).thenReturn(scoreboard);
        when(scoreBoardRepository.save(any(Scoreboard.class))).thenReturn(scoreboard);

        ResponseEntity<?> responseEntity = foodOrderController.addNewFoodOrder(dto, request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getPrice_Happy() {
        ResponseEntity<?> responseEntity = foodOrderController.getCurrentPrice();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getUsersByDate_Happy(){
        User user = new User(1,"Test", "test@test.nl", new Role(1, "ROLE_EMPLOYEE"));
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);

        List<FoodOrder> foodOrderList = new ArrayList<>();
        foodOrderList.add(new FoodOrder(1, user, date, true));

        when(foodOrderRepository.findAllByDate(any(Date.class))).thenReturn(foodOrderList);

        ResponseEntity<?> responseEntity = foodOrderController.getSimpleUserByDate(date);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
