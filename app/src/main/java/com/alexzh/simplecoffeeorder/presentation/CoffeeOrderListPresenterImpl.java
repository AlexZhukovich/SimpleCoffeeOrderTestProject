package com.alexzh.simplecoffeeorder.presentation;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.customview.CoffeeCountPicker;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderListView;
import com.alexzh.simplecoffeeorder.view.activity.CoffeeDetailActivity;
import com.alexzh.simplecoffeeorder.view.activity.CoffeeOrderDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class CoffeeOrderListPresenterImpl implements CoffeeOrderListPresenter {
    private static final int PAYMENT_REQUEST = 1;
    private final static String COFFEE_ORDERED_MAP = "coffee_ordered_map";
    private TreeMap<Coffee, Integer> mCoffeeOrderMap;

    private CoffeeOrderListView mView;
    private LocalBroadcastManager mBroadcastManager;
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;
    private Context mContext;

    public CoffeeOrderListPresenterImpl(Context context, CoffeeOrderListView coffeeOrderListView) {
        mContext = context;
        mView = coffeeOrderListView;
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext);

        mCoffeeOrderMap = new TreeMap<>();

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
        Intent intent = CoffeeOrderDetailsActivity.createIntent(mContext, mCoffeeOrderMap);
        if (calculatePrice() > 0) {
            ((Activity) mContext).startActivityForResult(intent, PAYMENT_REQUEST);
        } else {
            mView.displaySnackbar(R.string.message_more_coffee, Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void onPause() {
        mBroadcastManager.unregisterReceiver(mReceiver);
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
