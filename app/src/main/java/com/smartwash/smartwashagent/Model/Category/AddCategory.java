package com.smartwash.smartwashagent.Model.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddCategory implements Serializable
{

    @SerializedName("cate_name")
    @Expose
    private String cate_name;
    @SerializedName("cate_desc")
    @Expose
    private String cate_desc;
    @SerializedName("order_min_price")
    @Expose
    private String order_min_price;
    @SerializedName("cate_price")
    @Expose
    private int cate_price;

    public AddCategory(String cate_name, String cate_desc, String order_min_price,int cate_price) {
        this.cate_name = cate_name;
        this.cate_price = cate_price;
        this.cate_desc = cate_desc;
        this.order_min_price = order_min_price;
    }



}