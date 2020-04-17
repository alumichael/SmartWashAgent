package com.smartwash.smartwashagent.Model.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeleteCategory implements Serializable
{


    @SerializedName("cate_id")
    @Expose
    private int cate_id;

    public DeleteCategory(int cate_id) {

        this.cate_id = cate_id;
    }



}