package com.example.dicodingapp.model;

public class Favorite{

    private int key_id;
    private String title;
    private String image;
    private String status;

    public Favorite(){

    }

    public Favorite(int key_id, String title, String image, String status) {
        this.key_id = key_id;
        this.title = title;
        this.image = image;
        this.status = status;
    }

    public int getKey_id() {
        return key_id;
    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
