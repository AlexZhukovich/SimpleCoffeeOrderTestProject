package com.alexzh.simplecoffeeorder.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.alexzh.simplecoffeeorder.CoffeeAdapter;
import com.alexzh.simplecoffeeorder.CoffeeOrder;
import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.customview.CoffeeCountPicker;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.presentation.CoffeeOrderPresenter;
import com.alexzh.simplecoffeeorder.presentation.CoffeeOrderPresenterImpl;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderView;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CoffeeOrderView {
    private final static String COFFEE_COUNT = "coffee_count";

    private AppCompatTextView mTotalPriceToolBar;
    private RecyclerView mRecyclerView;
    private CoffeeAdapter mAdapter;

    private ProgressBar mProgressBar;

    private CoffeeOrderPresenter mCoffeeOrderPresenter;

    private HashMap<Coffee, Integer> mOrderedHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTotalPriceToolBar = (AppCompatTextView) findViewById(R.id.total_price_toolbar);
        mTotalPriceToolBar.setText(getString(R.string.price, 0.0f));

        mOrderedHashMap = new HashMap<>();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CoffeeAdapter(getApplicationContext(), null, new CoffeeAdapter.CoffeeOrderListener() {
            @Override
            public void onCoffeeChanged(Coffee coffee, CoffeeCountPicker.CoffeeOrderOperation operation, int count) {
                if (operation != CoffeeCountPicker.CoffeeOrderOperation.INIT) {
                    if (operation == CoffeeCountPicker.CoffeeOrderOperation.REMOVED && count == 0) {
                        mOrderedHashMap.remove(coffee);
                    } else {
                        mOrderedHashMap.put(coffee, count);
                    }
                }
                mTotalPriceToolBar.setText(getString(R.string.price, CoffeeOrder.calculateTotalPrice(mOrderedHashMap)));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        findViewById(R.id.pay).setOnClickListener(this);
        mCoffeeOrderPresenter = new CoffeeOrderPresenterImpl(this, this);

        startService(new Intent(this, CoffeeService.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay:
                startActivity(PaymentActivity.createIntent(getApplicationContext(), mOrderedHashMap));
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {

        }
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
    public void displayCoffeeList(List<Coffee> coffeeList) {
        if (mAdapter != null) {
            mAdapter.setCoffeeList(coffeeList);
            mAdapter.notifyDataSetChanged();
        }
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