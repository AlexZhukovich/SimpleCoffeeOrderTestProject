package com.alexzh.simplecoffeeorder.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.adapter.CoffeeOrderListAdapter;
import com.alexzh.simplecoffeeorder.customview.CoffeeCountPicker;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.presentation.CoffeeOrderListPresenter;
import com.alexzh.simplecoffeeorder.presentation.CoffeeOrderListPresenterImpl;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderListView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CoffeeOrderListActivity extends AppCompatActivity implements View.OnClickListener, CoffeeOrderListView {
    private AppCompatTextView mTotalPriceToolBar;
    private RecyclerView mRecyclerView;
    private CoffeeOrderListAdapter mAdapter;

    private ProgressBar mProgressBar;

    private CoffeeOrderListPresenter mCoffeeOrderListPresenter;

    private LocalBroadcastManager mBroadcastManager;
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_order_list);

        mTotalPriceToolBar = (AppCompatTextView) findViewById(R.id.total_price_toolbar);
        mTotalPriceToolBar.setText(getString(R.string.price, 0.0f));

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CoffeeOrderListAdapter(getApplicationContext(), null);
        mAdapter.setCoffeeOrderListener(new CoffeeOrderListAdapter.CoffeeOrderListener() {
            @Override
            public void onCoffeeChanged(Coffee coffee, CoffeeCountPicker.CoffeeOrderOperation operation, int count) {
                if (operation != CoffeeCountPicker.CoffeeOrderOperation.INIT) {
                    mCoffeeOrderListPresenter.updateCoffeeOrder(coffee, count, operation);
                }
            }
        });
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mRecyclerView.getChildAdapterPosition(v);
                mCoffeeOrderListPresenter.onClickCoffeeList(pos);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        findViewById(R.id.pay).setOnClickListener(this);
        mCoffeeOrderListPresenter = new CoffeeOrderListPresenterImpl(this, this);

        mBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<Coffee> coffeeList = (ArrayList<Coffee>) intent.getSerializableExtra(CoffeeService.INTENT_DATA);
                mCoffeeOrderListPresenter.updateData(coffeeList);
            }
        };
        mIntentFilter = new IntentFilter(CoffeeService.INTENT_GET_DATA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCoffeeOrderListPresenter.activityResults(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay:
                mCoffeeOrderListPresenter.showDetail();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCoffeeOrderListPresenter.savePresenterData(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCoffeeOrderListPresenter.restorePresenterData(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBroadcastManager.registerReceiver(mReceiver, mIntentFilter);
        mCoffeeOrderListPresenter.onResume();
    }

    @Override
    protected void onPause() {
        mBroadcastManager.unregisterReceiver(mReceiver);
        super.onPause();
    }

    @Override
    public void displayCoffeeList(TreeMap<Coffee, Integer> coffeeOrderMap) {
        if (mAdapter != null) {
            mAdapter.setCoffeeList(coffeeOrderMap);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void displayTotalPrice(float totalPrice) {
        mTotalPriceToolBar.setText(getString(R.string.price, totalPrice));
    }

    @Override
    public void displaySnackbar(int resId, int duration) {
        Snackbar.make(mRecyclerView, resId, duration).show();
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }
}