package com.alexzh.simplecoffeeorder.presentation;

import android.content.Intent;
import android.os.Bundle;

import com.alexzh.simplecoffeeorder.customview.CoffeeCountPicker;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderListView;

import java.util.ArrayList;

public interface CoffeeOrderListPresenter {

    void onResume();

    void setView(CoffeeOrderListView view);

    void updateCoffeeOrder(Coffee coffee, int count, CoffeeCountPicker.CoffeeOrderOperation operation);

    void savePresenterData(Bundle outState);

    void restorePresenterData(Bundle savedInstanceState);

    void activityResults(int requestCode, int resultCode, Intent data);

    void onClickCoffeeList(int position);

    void showDetail();

    void updateData(ArrayList<Coffee> coffeeList);

}
