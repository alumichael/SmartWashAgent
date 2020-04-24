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

    @SerializedName("cate_desc")
    @Expose
    private String cate_desc;

    @SerializedName("order_min_price")
    @Expose
    private String order_min_price;

    @SerializedName("cate_price")
    @Expose
    private String cate_price;

    public UpdateCategory(String cate_id ,String cate_name,String cate_desc,String order_min_price, String cate_price) {
        this.cate_id = cate_id;
        this.cate_name = cate_name;
        this.cate_price = cate_price;
        this.cate_desc = cate_desc;
        this.order_min_price = order_min_price;
    }



}