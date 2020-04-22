package com.foodplanner.rest_service.repositories;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import com.foodplanner.rest_service.databasemodel.User;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface FoodOrderRepository extends CrudRepository<FoodOrder, Integer> {
    List<FoodOrder> findAllByUser(User user);
}
