package com.smartwash.smartwashagent.Model.Profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Profile_updatePost implements Serializable {


    @SerializedName("fullname")
    @Expose
    public String fullname;

    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("uniqueID")
    @Expose
    public String uniqueID;

    public Profile_updatePost(String fullname, String phone, String uniqueID) {
        this.fullname = fullname;
        this.phone = phone;
        this.uniqueID = uniqueID;
    }

}
