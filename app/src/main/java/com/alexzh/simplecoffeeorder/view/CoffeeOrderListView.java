package com.alexzh.simplecoffeeorder.view;

import com.alexzh.simplecoffeeorder.model.Coffee;

import java.util.TreeMap;

public interface CoffeeOrderListView {

    void displayCoffeeList(TreeMap<Coffee, Integer> coffeeOrderMap);

    void displayTotalPrice(float totalPrice);

    void displaySnackbar(int resId, int duration);

    void showLoading();

    void hideLoading();

}
