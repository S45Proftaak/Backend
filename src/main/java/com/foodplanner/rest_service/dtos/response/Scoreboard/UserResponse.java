package com.foodplanner.rest_service.dtos.response.Scoreboard;

import com.foodplanner.rest_service.databasemodel.Role;

public class UserResponse {
    private Integer user_id;
    private String name;
    private Role role;

    public UserResponse(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
