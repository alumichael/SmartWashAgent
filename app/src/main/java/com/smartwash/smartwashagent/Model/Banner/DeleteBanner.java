package com.smartwash.smartwashagent.Model.Banner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeleteBanner implements Serializable
{


    @SerializedName("banner_id")
    @Expose
    private int banner_id;

    public DeleteBanner(int banner_id) {

        this.banner_id = banner_id;
    }



}