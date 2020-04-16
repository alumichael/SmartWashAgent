package com.smartwash.smartwashagent.Model.OrderStatusGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderStatusData implements Serializable
{

    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("orders")
    @Expose
    private List<OrderList> orders = null;


    public String getOrderID() {
        return orderID;
    }


    public String getUserID() {
        return userID;
    }



    public String getCategory() {
        return category;
    }



    public String getTotalAmount() {
        return totalAmount;
    }



    public String getStatus() {
        return status;
    }



    public List<OrderList> getOrders() {
        return orders;
    }

}