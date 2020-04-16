package com.smartwash.smartwashagent.Model.Errors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class APIError implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("errors")
    @Expose
    public List<String> errors ;


    public String getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }


}