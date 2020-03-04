package com.foodplanner.rest_service.databasemodel;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "food_order", schema = "DeVoedselbank")
public class FoodOrder {
    private int id;
    private int userid;
    private Date date;
    private byte toLate;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "to_late")
    public byte getToLate() {
        return toLate;
    }

    public void setToLate(byte toLate) {
        this.toLate = toLate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodOrder that = (FoodOrder) o;
        return id == that.id &&
                userid == that.userid &&
                toLate == that.toLate &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, date, toLate);
    }
}
