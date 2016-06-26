package com.alexzh.simplecoffeeorder.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.adapter.CoffeeOrderDetailListViewAdapter;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.presentation.CoffeeOrderDetailsPresenter;
import com.alexzh.simplecoffeeorder.presentation.CoffeeOrderDetailsPresenterImpl;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderDetailsView;

import java.util.HashMap;
import java.util.TreeMap;

public class CoffeeOrderDetailsActivity extends AppCompatActivity implements CoffeeOrderDetailsView, View.OnClickListener {
    public final static String ORDER_LIST = "order_list";

    private TextView mTotalPrice;
    private CoffeeOrderDetailListViewAdapter mAdapter;
    private CoffeeOrderDetailsPresenter mDetailPresenter;

    public static Intent createIntent(Context context, TreeMap<Coffee, Integer> orderList) {
        Intent intent = new Intent(context, CoffeeOrderDetailsActivity.class);
        intent.putExtra(ORDER_LIST, orderList);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_order_details);

        mTotalPrice = (TextView) findViewById(R.id.total_price_toolbar);
        mDetailPresenter = new CoffeeOrderDetailsPresenterImpl(getIntent(), this);

        ListView detailListView = (ListView) findViewById(R.id.detail_list);
        mAdapter = new CoffeeOrderDetailListViewAdapter(this, null);

        if (detailListView != null) {
            detailListView.setAdapter(mAdapter);
        }

        FloatingActionButton pay = (FloatingActionButton) findViewById(R.id.pay);
        if (pay != null) {
            pay.setOnClickListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDetailPresenter.onResume();
    }


    @Override
    public void displayOrderedCoffeeList(TreeMap<Coffee, Integer> coffeeMap) {
        mAdapter.setOrderedCoffee(coffeeMap);
    }

    @Override
    public void displayTotalPrice(float totalPrice) {
        mTotalPrice.setText(getString(R.string.price, totalPrice));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay:
                mDetailPresenter.payForCoffee(CoffeeOrderDetailsActivity.this);
                break;
        }
    }
}
