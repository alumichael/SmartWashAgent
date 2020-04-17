package com.smartwash.smartwashagent.Model.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateCategory implements Serializable
{

    @SerializedName("cate_id")
    @Expose
    private String cate_id;

    @SerializedName("cate_name")
    @Expose
    private String cate_name;
    @SerializedName("cate_price")
    @Expose
    private String cate_price;

    public UpdateCategory(String cate_id ,String cate_name, String cate_price) {
        this.cate_id = cate_id;
        this.cate_name = cate_name;
        this.cate_price = cate_price;
    }



}