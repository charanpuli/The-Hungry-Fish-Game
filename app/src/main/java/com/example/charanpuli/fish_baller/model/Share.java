package com.example.charanpuli.fish_baller.model;

public class Share {
    private String name,place,image,pid;
    private int score;

    public Share() {
    }

    public Share(String name, String place, int score, String image,String pid) {
        this.name = name;
        this.place = place;
        this.score = score;
        this.image = image;
        this.pid = pid;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }


}
