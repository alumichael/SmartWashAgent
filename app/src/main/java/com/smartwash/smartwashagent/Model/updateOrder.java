package com.smartwash.smartwashagent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class updateOrder implements Serializable
{

    @SerializedName("orderID")
    @Expose
    public String orderID;
    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("status")
    @Expose
    public String status;

    public updateOrder(String orderID, String userID, String status) {
        this.orderID = orderID;
        this.userID = userID;
        this.status = status;
    }
}