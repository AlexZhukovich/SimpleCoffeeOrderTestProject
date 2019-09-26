package com.alexzh.simplecoffeeorder.view.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.adapter.CoffeeOrderDetailListViewAdapter;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.presentation.CoffeeOrderDetailsPresenter;
import com.alexzh.simplecoffeeorder.presentation.CoffeeOrderDetailsPresenterImpl;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderDetailsView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.TreeMap;

public class CoffeeOrderDetailsActivity extends AppCompatActivity implements CoffeeOrderDetailsView, View.OnClickListener {
    private final static int NOTIFICATION_ID = 100;
    public final static String ORDER_LIST = "order_list";
    public final static String DELIVERY_INFO = "deliver_to";

    private TextView mTotalPrice;
    private EditText mDeliveryInfo;
    private CoffeeOrderDetailListViewAdapter mAdapter;
    private CoffeeOrderDetailsPresenter mDetailPresenter;

    public static Intent createIntent(Context context, TreeMap<Coffee, Integer> orderList, String deliverTo) {
        Intent intent = new Intent(context, CoffeeOrderDetailsActivity.class);
        intent.putExtra(ORDER_LIST, orderList);
        intent.putExtra(DELIVERY_INFO, deliverTo);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_order_details);

        mDeliveryInfo = findViewById(R.id.delivery_info);
        mTotalPrice = findViewById(R.id.total_price_toolbar);
        mDetailPresenter = new CoffeeOrderDetailsPresenterImpl(getIntent(), this);

        ListView detailListView = findViewById(R.id.detail_list);
        mAdapter = new CoffeeOrderDetailListViewAdapter(this, null);

        if (detailListView != null) {
            detailListView.setAdapter(mAdapter);
        }

        FloatingActionButton pay = findViewById(R.id.pay);
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
    public String getDeliveryInfo() {
        return mDeliveryInfo.getText().toString();
    }

    @Override
    public void displayDeliveryInfo(String deliveryInfo) {
        mDeliveryInfo.setText(getString(R.string.deliver_to_username, deliveryInfo));
    }

    @Override
    public void disableDeliveryInfo() {
        mDeliveryInfo.setEnabled(false);
    }

    @Override
    public void enableDeliveryInfo() {
        mDeliveryInfo.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay:
                mDetailPresenter.payForCoffee();
                break;
        }
    }

    @Override
    public void sendNotification(TreeMap<Coffee, Integer> order) {
        Intent intent = CoffeeOrderDetailsActivity.createIntent(getApplicationContext(), order, getDeliveryInfo());
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.drawable.ic_report_notif);
        mBuilder.setContentTitle("Coffee order app");
        mBuilder.setContentText("Thank you for your payment.");
        mBuilder.setContentIntent(pIntent);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

        mDetailPresenter.moveToOrderList();
    }

    @Override
    public void moveToOrderList() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
