package com.example.yztourguideapp.MODEL;

public class Tourmodel {


    private String imagedescr;
    private String imageurl;
    private String title;


    public Tourmodel(String title , String imagedescr, String imageurl) {
        this.imagedescr = imagedescr;
        this.imageurl = imageurl;
        this.title = title;
    }

    public Tourmodel() {
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getImagedescr() {
        return imagedescr;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImagedescr(String imagedescr) {
        this.imagedescr = imagedescr;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
