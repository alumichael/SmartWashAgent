package com.smartwash.smartwashagent.Model.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddCategory implements Serializable
{

    @SerializedName("cate_name")
    @Expose
    private String cate_name;
    @SerializedName("cate_price")
    @Expose
    private int cate_price;

    public AddCategory(String cate_name, int cate_price) {
        this.cate_name = cate_name;
        this.cate_price = cate_price;
    }



}