package com.alexzh.simplecoffeeorder.view;

import com.alexzh.simplecoffeeorder.model.Coffee;

import java.util.HashMap;
import java.util.List;

public interface CoffeeOrderView {

    void displayCoffeeList(HashMap<Coffee, Integer> coffeeOrderMap);

    void displayTotalPrice(float totalPrice);

    void showLoading();

    void hideLoading();

}
