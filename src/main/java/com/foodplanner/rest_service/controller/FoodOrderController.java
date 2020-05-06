package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.NewOrderDTO;
import com.foodplanner.rest_service.logic.foodorder.DateChecker;
import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.endpoints.OrderEndpoint;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import com.foodplanner.rest_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


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

    @GetMapping(value = OrderEndpoint.ALL_ORDERS)
    @ResponseBody
    public ResponseEntity<?> getFoodOrdersByUserID(HttpServletRequest req) {
        if(resolveToken(req)){
            User user = userRepository.findById(tokenProvider.getUserIdFromToken(resolvedToken)).get();
            return new ResponseEntity<>(foodOrderRepository.findAllByUser(user), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = OrderEndpoint.ORDERS_PER_WEEK)
    @ResponseBody
    public ResponseEntity<?> getFoodOrdersPerWeek(HttpServletRequest req, @RequestParam List<String> dates) {
        if (resolveToken(req)) {
            DateChecker checker = new DateChecker();
            return new ResponseEntity<>(checker.checkDates(foodOrderRepository.findAllByUser(userRepository.findById(tokenProvider.getUserIdFromToken(resolvedToken)).get()),
                    dates), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = OrderEndpoint.ADD_ORDER)
    @ResponseBody
    public ResponseEntity<?> addNewFoodOrder(HttpServletRequest req, @RequestBody NewOrderDTO newOrderDTO) {
        if (resolveToken(req)) {
            User user = userRepository.findById(tokenProvider.getUserIdFromToken(resolvedToken)).get();
            FoodOrder newOrder = new FoodOrder();
            newOrder.setUser(user);
            newOrder.setDate(newOrderDTO.getDate());
            foodOrderRepository.save(newOrder);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private boolean resolveToken(HttpServletRequest request) {
        resolvedToken = tokenProvider.resolveToken(request);
        return resolvedToken != null;
    }
}
