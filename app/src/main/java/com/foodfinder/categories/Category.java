package com.foodfinder.categories;

public class Category {
    private String name;
    private int quantity;
    private int thumbnail;

    public Category(String name, int quantity, int thumbnail) {
        this.name = name;
        this.quantity = quantity;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
