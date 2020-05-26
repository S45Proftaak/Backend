package com.foodplanner.rest_service.dtos.response.Scoreboard;

public class OwnScoreBoardResponse {
    private String name;
    private String email;
    private String role;
    private Long inTimePoints;
    private Integer positionInTimeRanking;
    private Long tooLatePoints;
    private Integer positionTooLateRanking;
    private Long totalPoints;
    private Integer positionGeneralRanking;

    public OwnScoreBoardResponse(String name, String email, String role, Long inTimePoints, Integer positionInTime, Long tooLatePoints, Integer positionTooLate, Integer positionGeneralRanking){
        this.name = name;
        this.email = email;
        this.role = role;
        this.inTimePoints = inTimePoints;
        this.positionInTimeRanking = positionInTime;
        this.tooLatePoints = tooLatePoints;
        this.positionTooLateRanking = positionTooLate;
        this.totalPoints = inTimePoints + tooLatePoints;
        this.positionGeneralRanking = positionGeneralRanking;
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

    public Integer getPositionInTime(){
        return positionInTimeRanking;
    }

    public Long getTooLatePoints() {
        return tooLatePoints;
    }

    public Integer getPositionTooLate(){
        return positionTooLateRanking;
    }

    public Long getTotalPoints() {
        return totalPoints;
    }

    public Integer getPositionGeneralRanking(){
        return positionGeneralRanking;
    }
}
