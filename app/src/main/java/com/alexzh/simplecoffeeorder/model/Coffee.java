package com.alexzh.simplecoffeeorder.model;

import java.io.Serializable;

public class Coffee implements Serializable {
    private String mName;
    private String[] mIngredients;
    private float mPrice;

    public Coffee(String name, String[] ingredients, float price) {
        this.mName = name;
        this.mPrice = price;
        this.mIngredients = ingredients;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String[] getIngredients() {
        return mIngredients;
    }

    public void setIngredients(String[] mIngredients) {
        this.mIngredients = mIngredients;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float mPrice) {
        this.mPrice = mPrice;
    }
}
