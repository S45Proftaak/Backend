package com.foodplanner.rest_service.endpoints;

public interface ScoreBoardEndpoint {
    public String BASE = "/scoreboard";
    public String GET_SCOREBOARD_MOST_EATEN = "/get-scoreboard-most-eaten";
    public String GET_SCOREBOARD_IN_TIME = "/get-scoreboard-in-time";
    public String GET_SCOREBOARD_TOO_LATE = "/get-scoreboard-too-late";
    public String GET_OWN_SCORES = "/get-own-scores";
}
