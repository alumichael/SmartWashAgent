package com.smartwash.smartwashagent.Model.ClothList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateCloth implements Serializable
{

    @SerializedName("cloth_id")
    @Expose
    private int cloth_id;

    @SerializedName("cloth_name")
    @Expose
    private String cloth_name;
    @SerializedName("cloth_amount")
    @Expose
    private String cloth_amount;

    public UpdateCloth(int cloth_id , String cloth_name, String cloth_amount) {
        this.cloth_id = cloth_id;
        this.cloth_name = cloth_name;
        this.cloth_amount = cloth_amount;
    }



}