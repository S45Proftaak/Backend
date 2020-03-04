package com.foodplanner.rest_service.repositories;

import com.foodplanner.rest_service.databasemodel.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
