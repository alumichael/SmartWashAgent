package com.smartwash.smartwashagent.Model.Transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class transactData implements Serializable
{

    @SerializedName("trans_ref")
    @Expose
    private String transRef;
    @SerializedName("trans_desc")
    @Expose
    private String transDesc;
    @SerializedName("trans_amt")
    @Expose
    private String transAmt;
    @SerializedName("trans_status")
    @Expose
    private String transStatus;


    public String getTransRef() {
        return transRef;
    }


    public String getTransDesc() {
        return transDesc;
    }



    public String getTransAmt() {
        return transAmt;
    }



    public String getTransStatus() {
        return transStatus;
    }


}