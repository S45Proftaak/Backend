package com.foodplanner.rest_service.ldap;

public class Person {
    private String uid;
    private String fullName;
    private String lastName;
    private String password;

    public Person() {
    }

    public Person(String fullName, String lastName, String password) {
        this.fullName = fullName;
        this.lastName = lastName;
        this.password = password;
    }

    public Person(String uid, String fullName, String lastName, String password) {
        this.uid = uid;
        this.fullName = fullName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fullName='" + fullName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
