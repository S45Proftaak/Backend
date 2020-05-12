package com.foodplanner.rest_service.logic.foodorder;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.databasemodel.User;
import com.foodplanner.rest_service.dtos.NewOrderDTO;
import com.foodplanner.rest_service.logic.scoreboard.PointDivider;
import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import com.foodplanner.rest_service.repositories.ScoreBoardRepository;

public class OrderHandler {
    PointDivider pointDivider;

    public OrderHandler(){
        pointDivider = new PointDivider();
    }

    public void handleExistingOrder(User user, NewOrderDTO newOrderDTO, Scoreboard scoreboard, FoodOrderRepository foodOrderRepository){
        if(foodOrderRepository.findByUserAndDate(user, newOrderDTO.getDate()).getToLate()){
            scoreboard.setPoints_too_late(pointDivider.removePointsTooLate(scoreboard.getPoints_too_late()));
        }else{
            scoreboard.setPoints_in_time(pointDivider.removePointsInTime(scoreboard.getPoints_in_time()));
        }
        foodOrderRepository.delete(foodOrderRepository.findByUserAndDate(user, newOrderDTO.getDate()));
    }

    public void handleNewOrder(User user, NewOrderDTO newOrderDTO, Scoreboard scoreboard, FoodOrderRepository foodOrderRepository, ScoreBoardRepository scoreBoardRepository){
        DateChecker checker = new DateChecker();
        boolean tooLate = checker.areYouToLate(newOrderDTO.getDate());
        FoodOrder newOrder = new FoodOrder();
        newOrder.setUser(user);
        newOrder.setDate(newOrderDTO.getDate());
        if (tooLate) {
            scoreboard.setPoints_too_late(pointDivider.addPointsTooLate(scoreboard.getPoints_too_late()));
        } else {
            scoreboard.setPoints_in_time(pointDivider.addPointsInTime(scoreboard.getPoints_in_time()));
        }
        newOrder.setToLate(tooLate);
        scoreBoardRepository.save(scoreboard);
        foodOrderRepository.save(newOrder);
    }
}
