package com.smartwash.smartwashagent.Model.LoginModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserPostData implements Serializable
{

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    public UserPostData(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }



}