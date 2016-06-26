package com.alexzh.simplecoffeeorder.model;

import java.io.Serializable;
import java.util.Arrays;

public class Coffee implements Serializable, Comparable<Coffee> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coffee coffee = (Coffee) o;

        if (Float.compare(coffee.mPrice, mPrice) != 0) return false;
        if (mName != null ? !mName.equals(coffee.mName) : coffee.mName != null) return false;
        return Arrays.equals(mIngredients, coffee.mIngredients);
    }

    @Override
    public int hashCode() {
        int result = mName != null ? mName.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(mIngredients);
        result = 31 * result + (mPrice != +0.0f ? Float.floatToIntBits(mPrice) : 0);
        return result;
    }

    @Override
    public int compareTo(Coffee another) {
        return this.mName.compareTo(another.getName());
    }
}
