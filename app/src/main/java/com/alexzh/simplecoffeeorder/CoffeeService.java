package com.alexzh.simplecoffeeorder;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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

    public static ArrayList<Coffee> getCoffeeData() {
        ArrayList<Coffee> list = new ArrayList<>();
        list.add(new Coffee(
                "Americano",
                new String[]{"Espresso", "hot water"},
                4.5f));
        list.add(new Coffee(
                "Cappuccino",
                new String[]{"Espresso", "steamed milk", "microfoam"},
                9.5f));
        list.add(new Coffee(
                "Espresso",
                new String[]{"Ground coffee beans", "hot water"},
                5.0f));
        list.add(new Coffee(
                "Espresso Macchiato",
                new String[]{"Espresso", "microfoam"},
                12.0f));
        list.add(new Coffee(
                "Latte",
                new String[]{"Espresso", "steamed milk", "microfoam"},
                5.5f));
        list.add(new Coffee(
                "Latte Macchiato",
                new String[]{"Steamed milk", "espresso", "microfoam"},
                9.0f));
        list.add(new Coffee(
                "Mocha",
                new String[]{"Espresso", "steamed milk", "chocolate"},
                7.5f));
        list.add(new Coffee(
                "White coffee",
                new String[]{"Brewed coffee", "milk"},
                6.0f));
        return list;
    }
}