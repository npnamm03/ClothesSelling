package com.example.miniproject_prm392.Models;

public class CategoryModel {
    String img_url;
    String name;
    String type;
    String id;
    public CategoryModel() {

    }

    public CategoryModel(String img_url, String name, String type) {
        this.img_url = img_url;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

