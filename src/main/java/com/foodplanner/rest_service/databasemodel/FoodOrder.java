package com.foodplanner.rest_service.databasemodel;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "food_order", schema = "DeVoedselbank")
public class FoodOrder {
    private int id;
    private User user;
    private Date date;
    private boolean toLate;

    //region id

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //endregion

    //region user many to one

    @ManyToOne(optional = false)
    @JoinColumn(name = "userid")
    public User getUser() {
        return user;
    }

    public void setUser(User userid) {
        this.user = userid;
    }

    //endregion

    //region date

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //endregion

    //region tolate

    @Basic
    @Column(name = "to_late")
    public boolean getToLate() {
        return toLate;
    }

    public void setToLate(boolean toLate) {
        this.toLate = toLate;
    }

    //endregion

    //region overrides

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodOrder that = (FoodOrder) o;
        return id == that.id &&
                user == that.user &&
                toLate == that.toLate &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, date, toLate);
    }

    //endregion

}
