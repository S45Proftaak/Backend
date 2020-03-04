package com.foodplanner.rest_service.repositories;

import com.foodplanner.rest_service.databasemodel.Meal;
import org.springframework.data.repository.CrudRepository;

public interface MealRepository extends CrudRepository<Meal, Integer> {
}
