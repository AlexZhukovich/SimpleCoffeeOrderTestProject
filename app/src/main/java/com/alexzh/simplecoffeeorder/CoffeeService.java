package com.alexzh.simplecoffeeorder;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;

import com.alexzh.simplecoffeeorder.model.Coffee;

import java.util.ArrayList;

public class CoffeeService extends IntentService {
    public final static String INTENT_GET_DATA = "com.alexzh.simplecoffeeorder.GET_DATA";
    public final static String INTENT_DATA = "data";

    public CoffeeService() {
        super("CoffeeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SystemClock.sleep(3000);

        Intent replyIntent = new Intent(INTENT_GET_DATA);
        replyIntent.putExtra(INTENT_DATA, getCoffeeData());

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.sendBroadcast(replyIntent);
    }

    public final static ArrayList<Coffee> getCoffeeData() {
        ArrayList<Coffee> list = new ArrayList<>();
        list.add(new Coffee("Espresso", 5.0f));
        list.add(new Coffee("Americano", 4.5f));
        list.add(new Coffee("Latte", 5.5f));
        list.add(new Coffee("Mocha", 7.5f));
        list.add(new Coffee("Cappuccino", 9.5f));
        list.add(new Coffee("Vacuum coffee", 12.0f));
        list.add(new Coffee("Café Cubano", 9.0f));
        list.add(new Coffee("Cafe Zorro", 6.0f));
        list.add(new Coffee("Guillermo", 3.0f));
        return list;
    }
}