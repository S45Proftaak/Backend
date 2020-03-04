package com.foodplanner.rest_service.databasemodel;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "food_order", schema = "DeVoedselbank")
public class FoodOrder {
    private int id;
    private User userid;
    private Date date;
    private byte toLate;

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

    //region meal one to one

    @OneToOne(optional = false)
    @JoinColumn(name = "meal", referencedColumnName = "id")
    private Meal meal;

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    //endregion

    //region user many to one

    @ManyToOne(optional = false)
    @JoinColumn(name = "userid")
    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
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
    public byte getToLate() {
        return toLate;
    }

    public void setToLate(byte toLate) {
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
                userid == that.userid &&
                toLate == that.toLate &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, date, toLate);
    }

    //endregion

}
