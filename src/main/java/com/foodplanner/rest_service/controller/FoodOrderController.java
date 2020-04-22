package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.NewOrderDTO;
import com.foodplanner.rest_service.dtos.WeekDTO;
import com.foodplanner.rest_service.logic.foodorder.DateChecker;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.mappings.OrderMapping;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController()
@RequestMapping(value = "/foodorder")
@CrossOrigin
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    private String resolvedToken = null;

    @GetMapping(value = OrderMapping.ALL_ORDERS)
    @ResponseBody
    public List<FoodOrder> getFoodOrdersByUserID(HttpServletRequest req) {
       resolveToken(req);

        User user = userRepository.findById(tokenProvider.getUserIdFromToken(resolvedToken)).get();
        return foodOrderRepository.findAllByUser(user);
    }

    @GetMapping(value = OrderMapping.ORDERS_PER_WEEK)
    @ResponseBody
    public ResponseEntity<?> getFoodOrdersPerWeek(HttpServletRequest req, @RequestParam List<String> dates) {
        resolveToken(req);

        DateChecker checker = new DateChecker();

        return new ResponseEntity<>(checker.checkDates(foodOrderRepository.findAllByUser(userRepository.findById(tokenProvider.getUserIdFromToken(resolvedToken)).get()),
                dates), HttpStatus.OK);
    }

    @PostMapping(value = OrderMapping.ADD_ORDER)
    @ResponseBody
    public ResponseEntity<?> addNewFoodOrder(HttpServletRequest req, @RequestBody NewOrderDTO newOrderDTO) {
        resolveToken(req);

        User user = userRepository.findById(tokenProvider.getUserIdFromToken(resolvedToken)).get();
        FoodOrder newOrder = new FoodOrder();
        newOrder.setUser(user);
        newOrder.setDate(newOrderDTO.getDate());
        foodOrderRepository.save(newOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<?> resolveToken(HttpServletRequest request) {
        try {
            resolvedToken = tokenProvider.resolveToken(request);
        } catch (Exception e) {
            Map map = new HashMap();
            map.put("error", "No token found");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
