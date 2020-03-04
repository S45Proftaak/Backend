package com.foodplanner.rest_service.repositories;

import com.foodplanner.rest_service.databasemodel.Menu;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<Menu, Integer> {
}
