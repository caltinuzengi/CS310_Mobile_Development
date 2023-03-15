package com.example.cs310newstrail;

import android.net.Uri;

import java.io.Serializable;

public class News implements Serializable {
    private int id;
    private String title;
    private String text;
    private String date;
    private String img;
    private String categoryName;

    public News(int id, String title, String text, String date, String img, String categoryName) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.img = img;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
