package com.example.yztourguideapp.MODEL;

public class Bookmodel {
    private String fullname;
    private String phonenumber;
    private String date;
    private String destination;
    private String imageurl;

    public Bookmodel() {
    }

    public Bookmodel(String fullname, String phonenumber, String date, String destination, String imageurl) {
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.date = date;
        this.destination = destination;
        this.imageurl = imageurl;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
