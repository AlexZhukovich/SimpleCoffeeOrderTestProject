package com.alexzh.simplecoffeeorder.view;

import com.alexzh.simplecoffeeorder.model.Coffee;

import java.util.List;

public interface CoffeeOrderView {

    void displayCoffeeList(List<Coffee> coffeeList);

    void showLoading();

    void hideLoading();

}
