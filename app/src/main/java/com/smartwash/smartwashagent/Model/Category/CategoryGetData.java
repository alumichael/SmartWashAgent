package com.smartwash.smartwashagent.Model.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryGetData implements Serializable
{

    @SerializedName("cate_id")
    @Expose
    private String cateId;
    @SerializedName("cate_name")
    @Expose
    private String cateName;
    @SerializedName("cate_desc")
    @Expose
    private String cateDesc;

    @SerializedName("cate_min_price")
    @Expose
    private String cateMinPrice;


    @SerializedName("cate_price")
    @Expose
    private String catePrice;

    public String getCateId() {
        return cateId;
    }



    public String getCateName() {
        return cateName;
    }

    public String getCateDesc() {
        return cateDesc;
    }

    public String getCateMinPrice() {
        return cateMinPrice;
    }
    public String getCatePrice() {
        return catePrice;
    }


}