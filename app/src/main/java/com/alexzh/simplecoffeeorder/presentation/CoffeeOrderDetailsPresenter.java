package com.alexzh.simplecoffeeorder.presentation;

import android.content.Context;

public interface CoffeeOrderDetailsPresenter {

    void onResume();

    float calculateTotalPrice();

    void payForCoffee(Context context);
}
