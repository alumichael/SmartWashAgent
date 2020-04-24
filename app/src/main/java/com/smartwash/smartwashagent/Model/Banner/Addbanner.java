package com.smartwash.smartwashagent.Model.Banner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Addbanner implements Serializable
{

    @SerializedName("banner_url")
    @Expose
    public String bannerUrl;

    public Addbanner(String bannerUrl) {

        this.bannerUrl = bannerUrl;
    }



}