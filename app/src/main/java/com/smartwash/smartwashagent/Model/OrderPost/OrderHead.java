package com.smartwash.smartwashagent.Model.OrderPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderHead implements Serializable
{

    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("data")
    @Expose
    public OrderData data;

    public OrderHead(String userID ,OrderData data) {
        this.userID = userID;
        this.data = data;


    }

    public String getUserID() {
        return userID;
    }


    public OrderData getData() {
        return data;
    }


}