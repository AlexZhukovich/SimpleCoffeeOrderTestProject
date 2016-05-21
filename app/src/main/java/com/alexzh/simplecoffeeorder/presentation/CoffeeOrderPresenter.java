package com.alexzh.simplecoffeeorder.presentation;

import com.alexzh.simplecoffeeorder.view.CoffeeOrderView;

/**
 * Created by alex on 5/19/16.
 */
public interface CoffeeOrderPresenter {

    void onResume();

    void loadCoffeeList();

    void setView(CoffeeOrderView view);

    void saveCoffeeOrderList();

    void onPause();

}
