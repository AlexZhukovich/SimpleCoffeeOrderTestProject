package com.alexzh.simplecoffeeorder.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.adapter.CoffeeAdapter;
import com.alexzh.simplecoffeeorder.customview.CoffeeCountPicker;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.presentation.CoffeeOrderPresenter;
import com.alexzh.simplecoffeeorder.presentation.CoffeeOrderPresenterImpl;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CoffeeOrderView {
    private final static String COFFEE_COUNT = "coffee_count";

    private AppCompatTextView mTotalPriceToolBar;
    private RecyclerView mRecyclerView;
    private CoffeeAdapter mAdapter;

    private ProgressBar mProgressBar;

    private CoffeeOrderPresenter mCoffeeOrderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTotalPriceToolBar = (AppCompatTextView) findViewById(R.id.total_price_toolbar);
        mTotalPriceToolBar.setText(getString(R.string.price, 0.0f));

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CoffeeAdapter(getApplicationContext(), null, new CoffeeAdapter.CoffeeOrderListener() {
            @Override
            public void onCoffeeChanged(Coffee coffee, CoffeeCountPicker.CoffeeOrderOperation operation, int count) {
                if (operation != CoffeeCountPicker.CoffeeOrderOperation.INIT) {
                    mCoffeeOrderPresenter.updateCoffeeOrder(coffee, count, operation);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        findViewById(R.id.pay).setOnClickListener(this);
        mCoffeeOrderPresenter = new CoffeeOrderPresenterImpl(this, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCoffeeOrderPresenter.activityResults(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay:
                mCoffeeOrderPresenter.showDetail();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCoffeeOrderPresenter.savePresenterData(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCoffeeOrderPresenter.restorePresenterData(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCoffeeOrderPresenter.onResume();
    }

    @Override
    protected void onPause() {
        mCoffeeOrderPresenter.onPause();
        super.onPause();
    }

    @Override
    public void displayCoffeeList(HashMap<Coffee, Integer> coffeeOrderMap) {
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