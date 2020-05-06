package com.foodplanner.rest_service.dtos.response.Scoreboard;

import com.foodplanner.rest_service.databasemodel.User;

public class ScoreboardResponse {
    public UserResponse user;
    private final Long totalPoints;

    public ScoreboardResponse(User user, Long totalPoints){
        this.totalPoints = totalPoints;
        setUser(user);
    }

    public UserResponse getUser() {
        return user;
    }

    public Long getTotalPoints() {
        return totalPoints;
    }

    public void setUser(User user){
        this.user = new UserResponse();
        this.user.setName(user.getName());
        this.user.setRole(user.getRole());
        this.user.setUser_id(user.getId());
    }
}
