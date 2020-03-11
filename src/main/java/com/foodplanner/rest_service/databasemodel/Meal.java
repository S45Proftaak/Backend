package com.foodplanner.rest_service.databasemodel;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "meals", schema = "DeVoedselbank")
public class Meal {
    private int id;
    private String name;
    private double price;
    private List<Menu> menu;

    @Id
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

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal that = (Meal) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    private FoodOrder foodOrder;

    @OneToOne(mappedBy = "meal")
    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    @OneToMany(mappedBy = "meal")
    public List<Menu> getMenu() { return menu; }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }
}
