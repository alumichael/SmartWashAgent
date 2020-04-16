package com.smartwash.smartwashagent.Model.Wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class fetchWallet implements Serializable

{

    @SerializedName("data")
    @Expose
    private fetchGetData data;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;


    public fetchGetData fetchGetData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

