package com.smartwash.smartwashagent.Model.LoginModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wallet  implements Serializable {

    @SerializedName("walletID")
    @Expose
    private String walletID;
    @SerializedName("amount")
    @Expose
    private String amount;


    public String getWalletID() {
        return walletID;
    }

    public String getAmount() {
        return amount;
    }


}
