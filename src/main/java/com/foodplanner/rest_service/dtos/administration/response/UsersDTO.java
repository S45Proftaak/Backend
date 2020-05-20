package com.foodplanner.rest_service.dtos.administration.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.foodplanner.rest_service.databasemodel.User;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class UsersDTO extends RepresentationModel<UsersDTO> {

    @JsonAlias("Users")
    private List<User> users;

    public UsersDTO(List<User> users) {
        this.users = users;
    }

    public UsersDTO() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
