package com.foodplanner.rest_service.repositories;

import com.foodplanner.rest_service.databasemodel.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
