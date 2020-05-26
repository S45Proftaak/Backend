package com.foodplanner.rest_service.controller;

import com.foodplanner.rest_service.configuration.PriceConfiguration;
import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.NewOrderDTO;
import com.foodplanner.rest_service.dtos.food_order.response.FoodOrderDTO;
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
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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
        class dto extends RepresentationModel<dto> { public List<FoodOrder> users; public dto(List<FoodOrder> users){ this.users = users; }}
        String resolvedToken = tokenProvider.resolveToken(request);
        User user = userRepository.findById(tokenProvider.getUserIdFromToken(resolvedToken)).get();
        Link self = linkTo(methodOn(this.getClass()).getFoodOrdersByUserID(request)).withSelfRel();
        List<FoodOrder> foodOrders = foodOrderRepository.findAllByUser(user);
        Link addOrderLink = linkTo(methodOn(this.getClass()).addNewFoodOrder(null, null)).withRel("add_order_date");
        return new ResponseEntity<>(new dto(foodOrders).add(self).add(addOrderLink), HttpStatus.OK);
    }

    @GetMapping(value = OrderEndpoint.ORDERS_PER_WEEK)
    @ResponseBody
    public ResponseEntity<?> getFoodOrdersPerWeek(@RequestParam List<String> dates, HttpServletRequest request) {
        String resolvedToken = tokenProvider.resolveToken(request);
        if(dates.size() > 5){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(FoodOrderController.class).getFoodOrdersPerWeek(dates, request)).withSelfRel());
        links.add(linkTo(methodOn(this.getClass()).addNewFoodOrder(null, null)).withRel("add_new_food_order"));
        DateChecker checker = new DateChecker();
        return new ResponseEntity<>(new FoodOrderDTO(checker.checkDates(foodOrderRepository.findAllByUser(userRepository.findById(tokenProvider.getUserIdFromToken(resolvedToken)).get()),
                dates)).add(links), HttpStatus.OK);
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

        Link self = linkTo(methodOn(this.getClass()).addNewFoodOrder(newOrderDTO, request)).withSelfRel();
        class response extends RepresentationModel<response> {}
        response res = new response();
        return new ResponseEntity<>(res.add(self), HttpStatus.OK);
    }

    @GetMapping(value = OrderEndpoint.GET_CURRENT_PRICE)
    public ResponseEntity<?> getCurrentPrice(){
        double currentPrice = configuration.getPrice();
        return new ResponseEntity(new GetPriceDTO(currentPrice), HttpStatus.OK);
    }
}
