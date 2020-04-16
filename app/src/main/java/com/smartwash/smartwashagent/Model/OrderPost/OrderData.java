package com.smartwash.smartwashagent.Model.OrderPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderData implements Serializable
{

    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("total_amount")
    @Expose
    public Integer totalAmount;
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("orders")
    @Expose
    public List<OrderedCloths> orders;

    public OrderData(String category, Integer totalAmount,String status, List<OrderedCloths> orders) {

        this.category = category;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orders = orders;
    }



    public String getCategory() {
        return category;
    }


    public Integer getTotalAmount() {
        return totalAmount;
    }


    public String getStatus() {
        return status;
    }


    public List<OrderedCloths> getOrder() {
        return orders;
    }



}