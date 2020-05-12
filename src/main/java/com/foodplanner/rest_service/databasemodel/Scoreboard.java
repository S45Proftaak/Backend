package com.foodplanner.rest_service.databasemodel;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;

@SuppressWarnings({"JpaDataSourceORMInspection", "JpaObjectClassSignatureInspection"})
@Entity
@Table(name = "scoreboard")
public class Scoreboard implements Comparable<Scoreboard>{

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
    private User user;


    public Integer getScoreboard_id() {
        return scoreboard_id;
    }

    public void setScoreboard_id(Integer scoreboard_id) {
        this.scoreboard_id = scoreboard_id;
    }

    public Long getPoints_in_time() {
        return points_in_time;
    }

    public void setPoints_in_time(Long points_in_time) {
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
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Scoreboard(){

    }

    public Scoreboard(Long points_in_time, Long points_too_late, User user){
        this.points_in_time = points_in_time;
        this.points_too_late = points_too_late;
        this.user = user;
    }

    @Override
    public int compareTo(Scoreboard o) {
        return this.getPoints_in_time().compareTo(o.getPoints_in_time());
    }
}
