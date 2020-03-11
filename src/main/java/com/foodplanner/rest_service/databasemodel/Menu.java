package com.foodplanner.rest_service.databasemodel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "menu", schema = "DeVoedselbank")
public class Menu {
    private int id;
    private Date date;
    private Meal meal;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "date")
    public Date getDate() { return date; }

    public void setDate(Date date) {
        this.date = date;
    }

   @ManyToOne(optional = false)
   @JoinColumn(name = "meal", referencedColumnName = "id")
    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
