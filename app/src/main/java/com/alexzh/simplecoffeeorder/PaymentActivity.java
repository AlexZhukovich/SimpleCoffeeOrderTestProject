package com.alexzh.simplecoffeeorder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {
    public final static String TOTAL_PRICE = "total_price";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        if (getIntent()!= null && getIntent().getExtras() != null) {
            float totalPrice = getIntent().getExtras().getFloat(TOTAL_PRICE);
            ((TextView) findViewById(R.id.payment_data)).setText(getString(R.string.total_price,
                    totalPrice));
        }
    }
}
