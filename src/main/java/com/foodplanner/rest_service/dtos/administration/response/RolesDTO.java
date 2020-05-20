package com.foodplanner.rest_service.dtos.administration.response;

import com.foodplanner.rest_service.databasemodel.Role;
import org.springframework.hateoas.RepresentationModel;

public class RolesDTO extends RepresentationModel<RolesDTO> {

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
