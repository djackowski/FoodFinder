package com.foodfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Food implements Serializable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Customer")
    @Expose
    private String customer;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("RestaurantName")
    @Expose
    private String restaurantName;
    @SerializedName("Latitude")
    @Expose
    private Float latitude;
    @SerializedName("Longitude")
    @Expose
    private Float longitude;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("Stars")
    @Expose
    private float stars;
    @SerializedName("Distance")
    @Expose
    private String distance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Food withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Food withName(String name) {
        this.name = name;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Food withCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Food withCategory(String category) {
        this.category = category;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Food withImage(String image) {
        this.image = image;
        return this;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Food withRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
        return this;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Food withLatitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Food withLongitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Food withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public Food withStars(float stars) {
        this.stars = stars;
        return this;
    }

}
