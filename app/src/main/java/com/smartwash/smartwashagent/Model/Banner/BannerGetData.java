package com.smartwash.smartwashagent.Model.Banner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BannerGetData implements Serializable
{

    @SerializedName("banner_id")
    @Expose
    private String bannerId;
    @SerializedName("banner_url")
    @Expose
    private String bannerUrl;


    public String getBannerId() {
        return bannerId;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }
}