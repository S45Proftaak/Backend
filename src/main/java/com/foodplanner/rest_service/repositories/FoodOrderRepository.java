package com.foodplanner.rest_service.repositories;

import com.foodplanner.rest_service.databasemodel.FoodOrder;
import org.springframework.data.repository.CrudRepository;

public interface FoodOrderRepository extends CrudRepository<FoodOrder, Integer> {
}
