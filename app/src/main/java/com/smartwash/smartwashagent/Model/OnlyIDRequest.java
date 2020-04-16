package com.smartwash.smartwashagent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OnlyIDRequest implements Serializable
{

    @SerializedName("userID")
    @Expose
    public String userID;

    public OnlyIDRequest(String userID) {

        this.userID = userID;
    }



}