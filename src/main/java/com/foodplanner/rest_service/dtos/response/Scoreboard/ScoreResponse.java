package com.foodplanner.rest_service.dtos.response.Scoreboard;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class ScoreResponse extends RepresentationModel<ScoreResponse> {

    private List<?> scores;

    public ScoreResponse(List<?> scores) {
        this.scores = scores;
    }

    public ScoreResponse() {
    }

    public List<?> getScores() {
        return scores;
    }

    public void setScores(List<?> scores) {
        this.scores = scores;
    }
}
