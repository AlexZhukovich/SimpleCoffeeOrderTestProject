package com.alexzh.simplecoffeeorder;

import com.alexzh.simplecoffeeorder.model.Coffee;

import java.util.HashMap;

public class CoffeeOrder {
    private float mCoffeePrice;
    private int mCoffeeCount;
    private float mTotalPrice;

    public CoffeeOrder(float coffeePrice) {
        mCoffeeCount = 0;
        mTotalPrice = 0;
        this.mCoffeePrice = coffeePrice;
    }

    public void setCoffeeCount(int count) {
        if (count >= 0) {
            this.mCoffeeCount = count;
        }
        calculateTotalPrice();
    }

    public int getCoffeeCount() {
        return mCoffeeCount;
    }

    public void incrementCoffeeCount() {
        mCoffeeCount++;
        calculateTotalPrice();
    }

    public float getTotalPrice() {
        return mTotalPrice;
    }

    public void decrementCoffeeCount() {
        if (mCoffeeCount > 0) {
            mCoffeeCount--;
            calculateTotalPrice();
        }
    }

    private void calculateTotalPrice() {
        mTotalPrice = mCoffeePrice * mCoffeeCount;
    }

    public static float calculateTotalPrice(HashMap<Coffee, Integer> orderHashMap) {
        float total = 0.0f;
        if (orderHashMap != null) {
            for (Coffee key : orderHashMap.keySet()) {
                total += key.getPrice() * orderHashMap.get(key);
            }
        }
        return total;
    }

    public static Coffee getCoffeeByPosition(HashMap<Coffee, Integer> orderedHashMap, int position) {
        int interactionPosition = 0;
        for (Coffee key : orderedHashMap.keySet()) {
            if (interactionPosition == position) {
                return  key;
            }
            interactionPosition++;
        }
        return null;
    }

    public static int getCountByCoffee(HashMap<Coffee, Integer> orderedHashMap, Coffee coffee) {
        for (Coffee key : orderedHashMap.keySet()) {
            if (key.equals(coffee)) {
                return  orderedHashMap.get(key);
            }
        }
        return 0;
    }
}
