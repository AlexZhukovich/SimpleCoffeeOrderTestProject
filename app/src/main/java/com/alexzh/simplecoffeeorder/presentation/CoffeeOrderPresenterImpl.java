package com.alexzh.simplecoffeeorder.presentation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.repository.CoffeeRepository;
import com.alexzh.simplecoffeeorder.repository.InMemoryCoffeeRepositoryImpl;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderView;

import java.util.ArrayList;

public class CoffeeOrderPresenterImpl implements CoffeeOrderPresenter {
    private CoffeeOrderView mView;
    private CoffeeRepository mCoffeeRepository;
    private LocalBroadcastManager mBroadcastManager;
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;
    private Context mContext;

    public CoffeeOrderPresenterImpl(Context context, CoffeeOrderView coffeeOrderView) {
        mContext = context;
        mView = coffeeOrderView;
        mCoffeeRepository = new InMemoryCoffeeRepositoryImpl();
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext);

        mView.showLoading();

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //output.setText(intent.getStringExtra(Coffe.KEY_OUTPUT));
                ArrayList<Coffee> coffeeList = (ArrayList<Coffee>) intent.getSerializableExtra(CoffeeService.INTENT_DATA);
                //Toast.makeText(mContext, "coffeeList.size() = "+coffeeList.size(), Toast.LENGTH_SHORT).show();
                if (coffeeList != null) {
                    mView.hideLoading();
                    mView.displayCoffeeList(coffeeList);
                }

                //TextView textView = (TextView) findViewById(R.id.textView);
                //textView.setText("Size: "+coffeeList.size());
            }
        };
        mIntentFilter = new IntentFilter(CoffeeService.INTENT_GET_DATA);
    }

    @Override
    public void onResume() {
        mBroadcastManager.registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void loadCoffeeList() {

    }

    @Override
    public void setView(CoffeeOrderView view) {
        mView = view;
    }

    @Override
    public void saveCoffeeOrderList() {

    }

    @Override
    public void onPause() {
        mBroadcastManager.unregisterReceiver(mReceiver);
    }
}
