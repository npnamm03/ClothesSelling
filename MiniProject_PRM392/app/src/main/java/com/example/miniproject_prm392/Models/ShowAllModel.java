package com.example.miniproject_prm392.Models;

import java.io.Serializable;

public class ShowAllModel implements Serializable {
    String description;
    String name;
    String rating;
    int price;
    String img_url;
    String typpe;

    public ShowAllModel() {
    }

    public ShowAllModel(String description, String name, String rating, int price, String img_url, String typpe) {
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.img_url = img_url;
        this.typpe = typpe;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTyppe() {
        return typpe;
    }

    public void setTyppe(String typpe) {
        this.typpe = typpe;
    }
}
