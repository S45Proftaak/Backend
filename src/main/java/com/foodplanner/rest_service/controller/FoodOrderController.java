package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.configuration.PriceConfiguration;
import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.NewOrderDTO;
import com.foodplanner.rest_service.dtos.food_order.response.GetPriceDTO;
import com.foodplanner.rest_service.logic.foodorder.DateChecker;
import com.foodplanner.rest_service.logic.foodorder.OrderHandler;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.endpoints.OrderEndpoint;
import com.foodplanner.rest_service.logic.scoreboard.PointDivider;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import com.foodplanner.rest_service.repositories.ScoreBoardRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@RestController()
@RequestMapping(value = OrderEndpoint.BASE)
@CrossOrigin
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreBoardRepository scoreBoardRepository;

    @Autowired
    private PriceConfiguration configuration;

    @GetMapping(value = OrderEndpoint.ALL_ORDERS)
    @ResponseBody
    public ResponseEntity<?> getFoodOrdersByUserID(HttpServletRequest request) {
        String resolvedToken = tokenProvider.resolveToken(request);
        User user = userRepository.findById(tokenProvider.getUserIdFromToken(resolvedToken)).get();
        return new ResponseEntity<>(foodOrderRepository.findAllByUser(user), HttpStatus.OK);
    }

    @GetMapping(value = OrderEndpoint.ORDERS_PER_WEEK)
    @ResponseBody
    public ResponseEntity<?> getFoodOrdersPerWeek(@RequestParam List<String> dates, HttpServletRequest request) {
        String resolvedToken = tokenProvider.resolveToken(request);
        if(dates.size() > 31 || dates.size() == 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DateChecker checker = new DateChecker();
        return new ResponseEntity<>(checker.checkDates(foodOrderRepository.findAllByUser(userRepository.findById(tokenProvider.getUserIdFromToken(resolvedToken)).get()),
                dates), HttpStatus.OK);
    }

    @PostMapping(value = OrderEndpoint.ADD_ORDER)
    @ResponseBody
    public ResponseEntity<?> addNewFoodOrder(@RequestBody NewOrderDTO newOrderDTO, HttpServletRequest request) {
        String resolvedToken = tokenProvider.resolveToken(request);
        User user = userRepository.findById(tokenProvider.getUserIdFromToken(resolvedToken)).get();
        Scoreboard scoreboard = scoreBoardRepository.findByUser(user);
        FoodOrder foundOrder = foodOrderRepository.findByUserAndDate(user, newOrderDTO.getDate());
        OrderHandler orderHandler = new OrderHandler();

        if(foundOrder != null){
           orderHandler.handleExistingOrder(user, newOrderDTO, scoreboard, foodOrderRepository);
        }else{
            orderHandler.handleNewOrder(user, newOrderDTO, scoreboard, foodOrderRepository, scoreBoardRepository);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = OrderEndpoint.GET_CURRENT_PRICE)
    public ResponseEntity<?> getCurrentPrice(){
        double currentPrice = configuration.getPrice();
        return new ResponseEntity(new GetPriceDTO(currentPrice), HttpStatus.OK);
    }

    @GetMapping(value = OrderEndpoint.GET_USERS_BY_DATE)
    public ResponseEntity<?> getSimpleUserByDate(@RequestParam Date date){
        class userName { public String name; public userName(String name){this.name = name;}}
        class dto {public List<userName> users; public dto(List<userName> users){this.users = users;}}
        Iterable<FoodOrder> order = foodOrderRepository.findAllByDate(date);
        List<userName> users = new ArrayList<>();
        for (FoodOrder food : order){
            User u = food.getUser();
            users.add(new userName(u.getName()));
        }
        return new ResponseEntity(new dto(users), HttpStatus.OK);
    }
}
