package com.foodplanner.rest_service.dtos.response.Scoreboard;

import com.foodplanner.rest_service.databasemodel.User;

public class ScoreboardResponse implements Comparable<ScoreboardResponse>{
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

    @Override
    public int compareTo(ScoreboardResponse o) {
        return this.totalPoints.compareTo(o.totalPoints);
    }

    @Override
    public boolean equals(Object t){
        if(!(t instanceof ScoreboardResponse)){
            return false;
        }
        ScoreboardResponse c = (ScoreboardResponse)t;
        //Compare however you want, ie
        return (c.getUser()).getName() == this.getUser().getName() && c.getUser().getRole() == this.getUser().getRole() && c.getUser().getUser_id() == this.getUser().getUser_id() && (c.getTotalPoints().equals(this.getTotalPoints()));
    }
}
