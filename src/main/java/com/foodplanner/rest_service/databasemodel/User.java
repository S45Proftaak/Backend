package com.foodplanner.rest_service.databasemodel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "user", schema = "DeVoedselbank")
@JsonIgnoreProperties({ "email", "scoreboard", "foodOrders"})
public class User {
    private int id;
    private String name;
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private Role role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role")

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    private Scoreboard scoreboard;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    public User(){

    }

    public User(Integer id, String name, String email, Role role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }


    private List<FoodOrder> foodOrders;

    @OneToMany(mappedBy = "user")

    public List<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    public void setFoodOrders(List<FoodOrder> foodOrder) {
        this.foodOrders = foodOrder;
    }
}
