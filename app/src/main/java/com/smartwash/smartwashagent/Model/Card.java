package com.smartwash.smartwashagent.Model;

public class Card {


    private String title;

    public Card(String title, int thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;

    }

    private int thumbnail;





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }


}
