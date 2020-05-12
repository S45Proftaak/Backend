package com.foodplanner.rest_service.repositories;

import com.foodplanner.rest_service.databasemodel.Scoreboard;
import com.foodplanner.rest_service.databasemodel.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreBoardRepository extends CrudRepository<Scoreboard, Integer> {
    Scoreboard findByUser(User user);

    @Query(value = "SELECT * FROM `scoreboard` ORDER BY points_in_time DESC LIMIT 20",
            nativeQuery = true)
    List<Scoreboard> getTopTwentyInTime();

    @Query(value = "SELECT * FROM `scoreboard` ORDER BY points_too_late DESC LIMIT 20",
            nativeQuery = true)
    List<Scoreboard> getTopTwentyTooLate();
}
