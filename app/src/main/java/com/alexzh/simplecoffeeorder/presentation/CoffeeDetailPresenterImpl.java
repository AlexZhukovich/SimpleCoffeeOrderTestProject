package com.alexzh.simplecoffeeorder.presentation;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.CoffeeDetailView;
import com.alexzh.simplecoffeeorder.view.activity.PaymentActivity;

import java.util.HashMap;

public class CoffeeDetailPresenterImpl implements CoffeeDetailPresenter {
    private static int NOTIFICATION_ID = 100;

    private CoffeeDetailView mView;

    private HashMap<Coffee, Integer> mCoffeeOrderMap;

    public CoffeeDetailPresenterImpl(Intent intent, CoffeeDetailView view) {
        this.mView = view;

        if (intent != null && intent.getExtras() != null) {
            mCoffeeOrderMap = (HashMap<Coffee, Integer>) intent.getExtras().getSerializable(PaymentActivity.ORDER_LIST);
        } else {
            mCoffeeOrderMap = new HashMap<>();
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
    public void payForCoffee(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_report_notif);
        mBuilder.setContentTitle("Coffee order app");
        mBuilder.setContentText("Thank you for your payment");
        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
