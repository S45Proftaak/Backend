package com.foodplanner.rest_service.databasemodel;

import javax.persistence.*;

@Entity
@Table(name = "menu", schema = "DeVoedselbank")
public class Menu {
    private int id;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    private Meal maandag;

    @OneToOne(optional = false)
    @JoinColumn(name = "maandag", referencedColumnName = "id")

    public Meal getMaandag() {
        return maandag;
    }

    public void setMaandag(Meal maandag) {
        this.maandag = maandag;
    }


    private Meal dinsdag;

    @OneToOne(optional = false)
    @JoinColumn(name = "dinsdag", referencedColumnName = "id")

    public Meal getDinsdag() {
        return maandag;
    }

    public void setDinsdag(Meal maandag) {
        this.maandag = maandag;
    }


    private Meal woensdag;

    @OneToOne(optional = false)
    @JoinColumn(name = "woensdag", referencedColumnName = "id")

    public Meal getWoensdag() {
        return maandag;
    }

    public void setWoensdag(Meal maandag) {
        this.maandag = maandag;
    }


    private Meal donderdag;

    @OneToOne(optional = false)
    @JoinColumn(name = "donderdag", referencedColumnName = "id")

    public Meal getDonderdag() {
        return maandag;
    }

    public void setDonderdag(Meal maandag) {
        this.maandag = maandag;
    }


    private Meal vrijdag;

    @OneToOne(optional = false)
    @JoinColumn(name = "vrijdag", referencedColumnName = "id")

    public Meal getVrijdag() {
        return maandag;
    }

    public void setVrijdag(Meal maandag) {
        this.maandag = maandag;
    }
}
