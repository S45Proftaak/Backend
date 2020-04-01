package com.foodplanner.rest_service.ldap;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

public class Person {

    private String uid;
    private String fullName;
    private String lastName;
    private String password;
    private String email;

    public Person() {
    }

    public Person(String fullName, String lastName, String password, String email) {
        this.fullName = fullName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    public Person(String uid, String fullName, String lastName, String password, String email) {
        this.uid = uid;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fullName='" + fullName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
