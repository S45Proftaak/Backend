package com.foodplanner.rest_service.repositories;

import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.databasemodel.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreBoardRepository extends JpaRepository<Scoreboard, Integer> {
    Scoreboard getByUser(User user);
}
