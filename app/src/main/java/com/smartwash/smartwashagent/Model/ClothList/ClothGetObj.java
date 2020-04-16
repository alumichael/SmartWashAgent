package com.smartwash.smartwashagent.Model.ClothList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ClothGetObj implements Serializable

{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ClothGetData> data = null;

    public String getStatus() {
        return status;
    }



    public String getMessage() {
        return message;
    }



    public List<ClothGetData> getData() {
        return data;
    }

    public void setData(List<ClothGetData> data) {
        this.data = data;
    }
}