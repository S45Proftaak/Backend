package com.foodplanner.rest_service.databasemodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "meal", schema = "DeVoedselbank")
public class Meal {
    private int id;
    private String name;
    private double price;

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

    @OneToOne(mappedBy = "meal", optional = false)
    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    private Menu menuVrijdag;

    @OneToOne(mappedBy = "vrijdag", optional = false)
    public Menu getMenuVrijdag() {
        return menuVrijdag;
    }

    public void setMenuVrijdag(Menu menu) {
        this.menuVrijdag = menu;
    }

    private Menu menuDonderdag;

    @OneToOne(mappedBy = "donderdag", optional = false)
    public Menu getMenuDonderdag() {
        return menuDonderdag;
    }

    public void setMenuDonderdag(Menu menuDonderdag) {
        this.menuDonderdag = menuDonderdag;
    }

    private Menu menuWoensdag;

    @OneToOne(mappedBy = "woensdag", optional = false)
    public Menu getMenuWoensdag() {
        return menuWoensdag;
    }

    public void setMenuWoensdag(Menu menuWoensdag) {
        this.menuWoensdag = menuWoensdag;
    }

    private Menu menuDinsdag;

    @OneToOne(mappedBy = "dinsdag", optional = false)
    public Menu getMenuDinsdag() {
        return menuDinsdag;
    }

    public void setMenuDinsdag(Menu menuDinsdag) {
        this.menuDinsdag = menuDinsdag;
    }

    private Menu menuMaandag;

    @OneToOne(mappedBy = "maandag", optional = false)
    public Menu getMenuMaandag() {
        return menuMaandag;
    }

    public void setMenuMaandag(Menu menuMaandag) {
        this.menuMaandag = menuMaandag;
    }
}
