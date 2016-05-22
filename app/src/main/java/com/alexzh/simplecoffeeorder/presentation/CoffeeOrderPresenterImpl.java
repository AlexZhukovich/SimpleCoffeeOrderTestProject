package com.alexzh.simplecoffeeorder.presentation;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.customview.CoffeeCountPicker;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderView;
import com.alexzh.simplecoffeeorder.view.activity.PaymentActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class CoffeeOrderPresenterImpl implements CoffeeOrderPresenter {
    private static final int PAYMENT_REQUEST = 1;
    private final static String COFFEE_ORDERED_MAP = "coffee_ordered_map";
    private HashMap<Coffee, Integer> mCoffeeOrderMap;

    private CoffeeOrderView mView;
    private LocalBroadcastManager mBroadcastManager;
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;
    private Context mContext;

    public CoffeeOrderPresenterImpl(Context context, CoffeeOrderView coffeeOrderView) {
        mContext = context;
        mView = coffeeOrderView;
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext);

        mCoffeeOrderMap = new HashMap<>();

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<Coffee> coffeeList = (ArrayList<Coffee>) intent.getSerializableExtra(CoffeeService.INTENT_DATA);
                for (Coffee coffee : coffeeList) {
                  mCoffeeOrderMap.put(coffee, 0);
                }
                if (coffeeList != null) {
                    mView.hideLoading();
                    mView.displayCoffeeList(mCoffeeOrderMap);
                    mView.displayTotalPrice(calculatePrice());
                }
            }
        };
        mIntentFilter = new IntentFilter(CoffeeService.INTENT_GET_DATA);
    }

    @Override
    public void onResume() {
        mBroadcastManager.registerReceiver(mReceiver, mIntentFilter);
        if (mCoffeeOrderMap == null || mCoffeeOrderMap.size() == 0) {
            mContext.startService(new Intent(mContext, CoffeeService.class));
            mView.showLoading();
        } else {
            mView.displayCoffeeList(mCoffeeOrderMap);
        }
    }

    @Override
    public void setView(CoffeeOrderView view) {
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
        mCoffeeOrderMap = (HashMap<Coffee, Integer>) savedInstanceState.getSerializable(COFFEE_ORDERED_MAP);
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
    public void showDetail() {
        Intent intent = PaymentActivity.createIntent(mContext, mCoffeeOrderMap);

        ((Activity) mContext).startActivityForResult(intent, PAYMENT_REQUEST);
    }

    @Override
    public void onPause() {
        mBroadcastManager.unregisterReceiver(mReceiver);
    }

    private float calculatePrice() {
        float total = 0.0f;
        HashMap<Coffee, Integer> coffeeOrderedMap = mCoffeeOrderMap;
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
