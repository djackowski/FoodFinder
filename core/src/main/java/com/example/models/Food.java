package com.example.models;

import java.io.Serializable;
import java.util.List;

public class Food implements Serializable {
    private int photoId;
    private String name;
    private float rating;
    private Restaurant restaurant;
    private List<Opinion> opinion;

    public Food(int photoId, String name, float rating, Restaurant restaurant, List<Opinion> opinion) {
        this.photoId = photoId;
        this.name = name;
        this.rating = rating;
        this.restaurant = restaurant;
        this.opinion = opinion;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Opinion> getOpinion() {
        return opinion;
    }

    public void setOpinion(List<Opinion> opinion) {
        this.opinion = opinion;
    }
}
