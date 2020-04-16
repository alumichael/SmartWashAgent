package com.smartwash.smartwashagent.Model.Wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class createWallet implements Serializable
{

    @SerializedName("userID")
    @Expose
    private String userID;

    public createWallet(String userID) {
        super();
        this.userID = userID;
    }



}