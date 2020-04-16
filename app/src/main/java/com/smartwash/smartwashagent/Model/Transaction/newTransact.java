package com.smartwash.smartwashagent.Model.Transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class newTransact implements Serializable
{

    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("trans_ref")
    @Expose
    public String trans_ref;
    @SerializedName("trans_desc")
    @Expose
    public String trans_desc;
    @SerializedName("trans_amt")
    @Expose
    public int trans_amt;

    public newTransact(String userID, String trans_ref, String trans_desc, int trans_amt) {

        this.userID = userID;
        this.trans_ref = trans_ref;
        this.trans_desc = trans_desc;
        this.trans_amt = trans_amt;
    }
}