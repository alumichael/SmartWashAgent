package com.smartwash.smartwashagent.Model;

public class ServiceCard {

    private String title;
    private int thumbnail;
    private String desc;

    public ServiceCard(String title, int thumbnail, String desc) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.desc = desc;

    }







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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
