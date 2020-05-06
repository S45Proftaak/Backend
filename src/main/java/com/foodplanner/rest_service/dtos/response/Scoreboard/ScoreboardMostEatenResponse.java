package com.foodplanner.rest_service.dtos.response.Scoreboard;

import com.foodplanner.rest_service.databasemodel.User;

public class ScoreboardMostEatenResponse {
    public UserResponse user;
    private final Long totalPoints;

    public ScoreboardMostEatenResponse(User user, Long totalPoints){
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
        this.user.setEmail(user.getEmail());
        this.user.setName(user.getName());
        this.user.setRole(user.getRole());
        this.user.setUser_id(user.getId());
    }
}
