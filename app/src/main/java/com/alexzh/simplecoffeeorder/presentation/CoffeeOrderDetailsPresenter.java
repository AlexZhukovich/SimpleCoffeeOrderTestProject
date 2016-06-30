package com.alexzh.simplecoffeeorder.presentation;

public interface CoffeeOrderDetailsPresenter {

    void onResume();

    float calculateTotalPrice();

    void payForCoffee();

    void moveToOrderList();
}
