package com.foodplanner.rest_service.repositories;

import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.databasemodel.User;
import org.springframework.data.repository.CrudRepository;

public interface ScoreBoardRepository extends CrudRepository<Scoreboard, Integer> {
    Scoreboard findByUser(User user);
}
