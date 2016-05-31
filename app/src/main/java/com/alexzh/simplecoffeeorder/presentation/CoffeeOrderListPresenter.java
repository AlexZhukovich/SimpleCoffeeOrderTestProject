package com.alexzh.simplecoffeeorder.presentation;

import android.content.Intent;
import android.os.Bundle;

import com.alexzh.simplecoffeeorder.customview.CoffeeCountPicker;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderView;

/**
 * Created by alex on 5/19/16.
 */
public interface CoffeeOrderListPresenter {

    void onResume();

    void setView(CoffeeOrderView view);

    void updateCoffeeOrder(Coffee coffee, int count, CoffeeCountPicker.CoffeeOrderOperation operation);

    void savePresenterData(Bundle outState);

    void restorePresenterData(Bundle savedInstanceState);

    void activityResults(int requestCode, int resultCode, Intent data);

    void showDetail();

    void onPause();

}
