package com.foodplanner.rest_service.databasemodel;

import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;

@SuppressWarnings({"JpaDataSourceORMInspection", "JpaObjectClassSignatureInspection"})
@Entity
@Table(name = "scoreboard")
public class Scoreboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "scoreboard_id")
    private Integer scoreboard_id;

    @Column(name = "points_in_time")
    private Long points_in_time;

    @Column(name = "points_too_late")
    private Long points_too_late;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User scoreBoardUser;


    public Integer getScoreboard_id() {
        return scoreboard_id;
    }

    public void setScoreboard_id(Integer scoreboard_id) {
        this.scoreboard_id = scoreboard_id;
    }

    public Long getPoints() {
        return points_in_time;
    }

    public void setPoints(Long points_in_time) {
        this.points_in_time = points_in_time;
    }

    public Long getPoints_too_late() {
        return points_too_late;
    }

    public void setPoints_too_late(Long points_too_late) {
        this.points_too_late = points_too_late;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return scoreBoardUser;
    }

    public void setUser(User user) {
        this.scoreBoardUser = user;
    }

    public Scoreboard(Long points_in_time, Long points_too_late, User user){
        this.points_in_time = points_in_time;
        this.points_too_late = points_too_late;
        this.scoreBoardUser = user;
    }
}
