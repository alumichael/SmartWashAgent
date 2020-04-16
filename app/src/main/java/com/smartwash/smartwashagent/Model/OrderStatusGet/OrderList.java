package com.smartwash.smartwashagent.Model.OrderStatusGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderList implements Serializable
{

    @SerializedName("cloth")
    @Expose
    private String cloth;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;


    public String getCloth() {
        return cloth;
    }



    public Integer getQuantity() {
        return quantity;
    }



}