package com.smartwash.smartwashagent.Model.Wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class fetchGetData implements Serializable
{

    @SerializedName("walletID")
    @Expose
    private String walletID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getWalletID() {
        return walletID;
    }

    public String getUserID() {
        return userID;
    }

    public String getAmount() {
        return amount;
    }
}