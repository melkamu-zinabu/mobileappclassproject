package com.example.yztourguideapp.MODEL;

public class Helpmodel {
    private String title;
    private String description;



    public Helpmodel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Helpmodel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}