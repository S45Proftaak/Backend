package com.foodplanner.rest_service.dtos.response.Scoreboard;

public class OwnScoreBoardResponse {
    private String name;
    private String email;
    private String role;
    private Long inTimePoints;
    private Long tooLatePoints;
    private Long totalPoints;

    public OwnScoreBoardResponse(String name, String email, String role, Long inTimePoints, Long tooLatePoints){
        this.name = name;
        this.email = email;
        this.role = role;
        this.inTimePoints = inTimePoints;
        this.tooLatePoints = tooLatePoints;
        this.totalPoints = inTimePoints + tooLatePoints;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public Long getInTimePoints() {
        return inTimePoints;
    }

    public Long getTooLatePoints() {
        return tooLatePoints;
    }

    public Long getTotalPoints() {
        return totalPoints;
    }
}
