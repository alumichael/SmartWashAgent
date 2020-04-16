package com.smartwash.smartwashagent.Model.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterGetData implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("unique_id")
    @Expose
    private String unique_id;
    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address")
    @Expose
    private String address;



    @SerializedName("role")
    @Expose
    private String role;


    public String getId() {
        return id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getRole() {
        return role;
    }
}