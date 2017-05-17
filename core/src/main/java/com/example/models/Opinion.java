package com.example.models;

import java.io.Serializable;

public class Opinion implements Serializable {
    private String personName;
    private String opinion;
    private float rating;

    public Opinion(String personName, String opinion, float rating) {
        this.personName = personName;
        this.opinion = opinion;
        this.rating = rating;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
