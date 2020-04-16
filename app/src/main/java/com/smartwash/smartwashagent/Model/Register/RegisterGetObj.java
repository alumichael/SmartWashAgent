package com.smartwash.smartwashagent.Model.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class RegisterGetObj implements Serializable
{

    @SerializedName("user")
    @Expose
    private RegisterGetData user;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public RegisterGetData getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}