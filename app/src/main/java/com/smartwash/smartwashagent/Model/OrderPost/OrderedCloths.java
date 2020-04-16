package com.smartwash.smartwashagent.Model.OrderPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderedCloths implements Serializable
{

    @SerializedName("cloth")
    @Expose
    public String cloth;
    @SerializedName("quantity")
    @Expose
    public Integer quantity;



    public OrderedCloths(String cloth, Integer quantity) {

        this.cloth = cloth;
        this.quantity = quantity;
    }

    public String getCloth() {
        return cloth;
    }

    public void setCloth(String cloth) {
        this.cloth = cloth;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}