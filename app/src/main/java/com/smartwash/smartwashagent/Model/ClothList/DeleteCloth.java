package com.smartwash.smartwashagent.Model.ClothList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeleteCloth implements Serializable
{


    @SerializedName("cloth_id")
    @Expose
    private int cloth_id;

    public DeleteCloth(int cloth_id) {

        this.cloth_id = cloth_id;
    }



}