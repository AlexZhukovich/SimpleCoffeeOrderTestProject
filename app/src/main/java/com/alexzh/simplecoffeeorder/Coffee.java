package com.alexzh.simplecoffeeorder;

import java.io.Serializable;

public class Coffee implements Serializable {
    private String mName;
    private float mPrice;

    public Coffee(String name, float price) {
        this.mName = name;
        this.mPrice = price;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        this.mPrice = price;
    }
}
