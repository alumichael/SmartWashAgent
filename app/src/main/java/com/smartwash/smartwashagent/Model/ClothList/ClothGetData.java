package com.smartwash.smartwashagent.Model.ClothList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClothGetData implements Serializable
{

    @SerializedName("cloth_id")
    @Expose
    private String clothId;
    @SerializedName("cloth_name")
    @Expose
    private String clothName;

    @SerializedName("cloth_amount")
    @Expose
    private String clothAmount;



    @SerializedName("quantity")
    @Expose
    private int quantity;

    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getClothId() {
        return clothId;
    }

    public String getClothName() {
        return clothName;
    }
    public String getClothAmount() {
        return clothAmount;
    }


    public void setClothName(String clothName) {
        this.clothName = clothName;
    }

    public void setClothAmount(String clothAmount) {
        this.clothAmount = clothAmount;
    }
}