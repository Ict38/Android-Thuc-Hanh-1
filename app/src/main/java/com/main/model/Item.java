package com.main.model;

public class Item {
    private int img;
    private String job;
    private String date;

    public Item() {
    }

    public Item(int img, String job, String date) {
        this.img = img;
        this.job = job;
        this.date = date;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
