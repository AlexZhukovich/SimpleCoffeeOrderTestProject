package com.alexzh.simplecoffeeorder.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.customview.CoffeeCountPicker;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderListView;
import com.alexzh.simplecoffeeorder.view.activity.CoffeeDetailActivity;
import com.alexzh.simplecoffeeorder.view.activity.CoffeeOrderDetailsActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.TreeMap;

public class CoffeeOrderListPresenterImpl implements CoffeeOrderListPresenter {
    private static final int PAYMENT_REQUEST = 1;
    private final static String COFFEE_ORDERED_MAP = "coffee_ordered_map";
    private TreeMap<Coffee, Integer> mCoffeeOrderMap;

    private CoffeeOrderListView mView;
    private Context mContext;

    public CoffeeOrderListPresenterImpl(Context context, CoffeeOrderListView coffeeOrderListView) {
        mContext = context;
        mView = coffeeOrderListView;
        mCoffeeOrderMap = new TreeMap<>();
    }

    @Override
    public void onResume() {
        if (mCoffeeOrderMap == null || mCoffeeOrderMap.size() == 0) {
            mContext.startService(new Intent(mContext, CoffeeService.class));
            mView.showLoading();
        } else {
            mView.displayCoffeeList(mCoffeeOrderMap);
        }
    }

    @Override
    public void setView(CoffeeOrderListView view) {
        mView = view;
    }

    @Override
    public void updateCoffeeOrder(Coffee coffee, int count, CoffeeCountPicker.CoffeeOrderOperation operation) {
        if (mCoffeeOrderMap != null && mCoffeeOrderMap.size() > 0) {
            mCoffeeOrderMap.put(coffee, count);
        }
        mView.displayTotalPrice(calculatePrice());
    }

    @Override
    public void savePresenterData(Bundle outState) {
        outState.putSerializable(COFFEE_ORDERED_MAP, mCoffeeOrderMap);
    }

    @Override
    public void restorePresenterData(Bundle savedInstanceState) {
        mCoffeeOrderMap = (TreeMap<Coffee, Integer>) savedInstanceState.getSerializable(COFFEE_ORDERED_MAP);
        mView.displayTotalPrice(calculatePrice());
    }

    @Override
    public void activityResults(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            cleanup();
            mView.displayCoffeeList(mCoffeeOrderMap);
            mView.displayTotalPrice(calculatePrice());
        }
    }

    @Override
    public void onClickCoffeeList(int position) {
        Coffee coffee = (Coffee) mCoffeeOrderMap.keySet().toArray()[position];
        mContext.startActivity(CoffeeDetailActivity.createCoffeeDetailIntent(mContext, coffee));
    }

    @Override
    public void showDetail() {
        Intent intent = CoffeeOrderDetailsActivity.createIntent(mContext, mCoffeeOrderMap, null);
        if (calculatePrice() > 0) {
            ((Activity) mContext).startActivityForResult(intent, PAYMENT_REQUEST);
        } else {
            mView.displaySnackbar(R.string.message_more_coffee, Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void updateData(ArrayList<Coffee> coffeeList) {
        for (Coffee coffee : coffeeList) {
            mCoffeeOrderMap.put(coffee, 0);
        }
        if (coffeeList != null) {
            mView.hideLoading();
            mView.displayCoffeeList(mCoffeeOrderMap);
            mView.displayTotalPrice(calculatePrice());
        }
    }

    private float calculatePrice() {
        float total = 0.0f;
        TreeMap<Coffee, Integer> coffeeOrderedMap = mCoffeeOrderMap;
        if (coffeeOrderedMap != null) {
            for (Coffee key : coffeeOrderedMap.keySet()) {
                total += key.getPrice() * coffeeOrderedMap.get(key);
            }
        }
        return total;
    }

    private void cleanup() {
        for (Coffee coffee : mCoffeeOrderMap.keySet()) {
            mCoffeeOrderMap.put(coffee, 0);
        }
    }
}
