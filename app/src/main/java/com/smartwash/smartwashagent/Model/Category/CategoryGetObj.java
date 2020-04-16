package com.smartwash.smartwashagent.Model.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class CategoryGetObj implements Serializable

{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<CategoryGetData> data = null;

    public String getStatus() {
        return status;
    }



    public String getMessage() {
        return message;
    }



    public List<CategoryGetData> getData() {
        return data;
    }


}