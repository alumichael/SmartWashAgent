package com.smartwash.smartwashagent.Model.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterUserPost implements Serializable {

    @SerializedName("fullname")
    @Expose
    public String fullname;

    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("address")
    @Expose
    public String address;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("role")
    @Expose
    public String role;

    public RegisterUserPost(String fullname, String email, String phone,String address, String password,String role) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.role = role;
    }



}