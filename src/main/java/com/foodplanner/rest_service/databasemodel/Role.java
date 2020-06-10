package com.foodplanner.rest_service.databasemodel;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "role", schema = "DeVoedselbank")
public class Role {
    private int id;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role that = (Role) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    private Collection<User> users;

    @OneToMany(mappedBy = "role")
    @JsonBackReference
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> user) {
        this.users = user;
    }

    public Role(Integer id, String name){
        this.id = id;
        this.name = name;
    }
}
