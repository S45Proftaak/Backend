package com.foodplanner.rest_service.dtos.administration.response;

import com.foodplanner.rest_service.databasemodel.Role;

public class RolesDTO {

    public Iterable<Role> roles;

    public RolesDTO(Iterable<Role> roles) {
        this.roles = roles;
    }

    public RolesDTO() {
    }

    public Iterable<Role> getRoles() {
        return roles;
    }

    public void setRoles(Iterable<Role> roles) {
        this.roles = roles;
    }
}
