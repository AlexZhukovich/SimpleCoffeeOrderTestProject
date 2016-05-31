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
import com.alexzh.simplecoffeeorder.adapter.DetailListViewAdapter;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.presentation.CoffeeDetailPresenter;
import com.alexzh.simplecoffeeorder.presentation.CoffeeDetailPresenterImpl;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderDetailsView;

import java.util.HashMap;

public class CoffeeOrderDetailsActivity extends AppCompatActivity implements CoffeeOrderDetailsView, View.OnClickListener {
    public final static String ORDER_LIST = "order_list";

    private TextView mTotalPrice;
    private DetailListViewAdapter mAdapter;
    private CoffeeDetailPresenter mDetailPresenter;

    public static Intent createIntent(Context context, HashMap<Coffee, Integer> orderList) {
        Intent intent = new Intent(context, CoffeeOrderDetailsActivity.class);
        intent.putExtra(ORDER_LIST, orderList);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mTotalPrice = (TextView) findViewById(R.id.total_price_toolbar);
        mDetailPresenter = new CoffeeDetailPresenterImpl(getIntent(), this);

        ListView detailListView = (ListView) findViewById(R.id.detail_list);
        mAdapter = new DetailListViewAdapter(this, null);

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
    public void displayOrderedCoffeeList(HashMap<Coffee, Integer> coffeeMap) {
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
