package com.foodplanner.rest_service.dtos.administration.request;

public class UpdateUserRoleDTO {

    private int userID;
    private int roleID;

    public UpdateUserRoleDTO(int userID, int roleID) {
        this.userID = userID;
        this.roleID = roleID;
    }

    public UpdateUserRoleDTO() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}
