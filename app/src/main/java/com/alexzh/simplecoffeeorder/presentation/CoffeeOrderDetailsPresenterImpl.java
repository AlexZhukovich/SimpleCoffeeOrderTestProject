package com.alexzh.simplecoffeeorder.presentation;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.CoffeeOrderDetailsView;
import com.alexzh.simplecoffeeorder.view.activity.CoffeeOrderDetailsActivity;

import java.util.HashMap;
import java.util.TreeMap;

public class CoffeeOrderDetailsPresenterImpl implements CoffeeOrderDetailsPresenter {
    private final static int NOTIFICATION_ID = 100;

    private CoffeeOrderDetailsView mView;

    private TreeMap<Coffee, Integer> mCoffeeOrderMap;

    public CoffeeOrderDetailsPresenterImpl(Intent intent, CoffeeOrderDetailsView view) {
        this.mView = view;

        if (intent != null && intent.getExtras() != null) {
            mCoffeeOrderMap = new TreeMap<>();
            TreeMap<Coffee, Integer> orderedCoffeeMap  = (TreeMap<Coffee, Integer>) intent.getExtras().getSerializable(CoffeeOrderDetailsActivity.ORDER_LIST);
            if (orderedCoffeeMap != null && orderedCoffeeMap.size() > 0) {
                for (Coffee coffee : orderedCoffeeMap.keySet()) {
                    if (orderedCoffeeMap.get(coffee) != 0) {
                        mCoffeeOrderMap.put(coffee, orderedCoffeeMap.get(coffee));
                    }
                }
            }
        } else {
            mCoffeeOrderMap = new TreeMap<>();
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
        sendNotification(context);

        ((Activity)context).setResult(Activity.RESULT_OK);
        ((Activity) context).finish();
    }

    private void sendNotification(Context context) {
        Intent intent = CoffeeOrderDetailsActivity.createIntent(context, mCoffeeOrderMap);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_report_notif);
        mBuilder.setContentTitle("Coffee order app");
        mBuilder.setContentText("Thank you for your payment.");
        mBuilder.setContentIntent(pIntent);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
