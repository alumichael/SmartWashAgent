package com.smartwash.smartwashagent.Model.Transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VerifyTransact implements Serializable
{

    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("trans_ref")
    @Expose
    public String trans_ref;


    public VerifyTransact(String userID, String trans_ref) {
        this.userID = userID;
        this.trans_ref = trans_ref;
    }
}