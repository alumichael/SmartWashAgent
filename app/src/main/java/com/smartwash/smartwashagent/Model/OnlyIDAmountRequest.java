package com.smartwash.smartwashagent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OnlyIDAmountRequest implements Serializable
{

    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("amount")
    @Expose
    public int amount;

    public OnlyIDAmountRequest(String userID,int amount) {

        this.userID = userID;
        this.amount = amount;
    }



}