package com.alexzh.simplecoffeeorder.presentation;

import android.content.Intent;

import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderDetailsView;
import com.alexzh.simplecoffeeorder.view.activity.CoffeeOrderDetailsActivity;

import java.util.HashMap;
import java.util.TreeMap;

public class CoffeeOrderDetailsPresenterImpl implements CoffeeOrderDetailsPresenter {

    private CoffeeOrderDetailsView mView;

    private TreeMap<Coffee, Integer> mCoffeeOrderMap;

    public CoffeeOrderDetailsPresenterImpl(Intent intent, CoffeeOrderDetailsView view) {
        this.mView = view;

        if (intent != null && intent.getExtras() != null) {
            mCoffeeOrderMap = new TreeMap<>();
            TreeMap<Coffee, Integer> orderedCoffeeMap  = new TreeMap<>((HashMap<Coffee, Integer>) intent.getExtras().getSerializable(CoffeeOrderDetailsActivity.ORDER_LIST));
            if (orderedCoffeeMap != null && orderedCoffeeMap.size() > 0) {
                for (Coffee coffee : orderedCoffeeMap.keySet()) {
                    if (orderedCoffeeMap.get(coffee) != 0) {
                        mCoffeeOrderMap.put(coffee, orderedCoffeeMap.get(coffee));
                    }
                }
            }

            if (intent.getStringExtra(CoffeeOrderDetailsActivity.DELIVERY_INFO) != null) {
                mView.displayDeliveryInfo(intent.getStringExtra(CoffeeOrderDetailsActivity.DELIVERY_INFO));
                mView.disableDeliveryInfo();
            } else {
                mView.enableDeliveryInfo();
            }
        } else {
            mCoffeeOrderMap = new TreeMap<>();
        }
    }

    @Override
    public void onResume() {
        mView.displayOrderedCoffeeList(mCoffeeOrderMap);
        mView.displayTotalPrice(calculateTotalPrice());
    }

    @Override
    public float calculateTotalPrice() {
        if (mCoffeeOrderMap.size() > 0) {
            float total = 0.0f;
            if (mCoffeeOrderMap != null) {
                for (Coffee key : mCoffeeOrderMap.keySet()) {
                    total += key.getPrice() * mCoffeeOrderMap.get(key);
                }
            }
            return total;
        }
        return 0;
    }

    @Override
    public void payForCoffee() {
        mView.sendNotification(mCoffeeOrderMap);
    }

    @Override
    public void moveToOrderList() {
        mView.moveToOrderList();
    }
}
