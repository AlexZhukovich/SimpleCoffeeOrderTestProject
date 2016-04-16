package com.alexzh.simplecoffeeorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {
    public final static String TOTAL_PRICE = "total_price";

    public static Intent createIntent(Context context, float totalPrice) {
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(TOTAL_PRICE, totalPrice);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        if (getIntent()!= null && getIntent().getExtras() != null) {
            float totalPrice = getIntent().getExtras().getFloat(TOTAL_PRICE);
            TextView paymentData = (TextView) findViewById(R.id.payment_data);

            if (paymentData != null) {
                paymentData.setText(getString(R.string.total_price, totalPrice));
            }
        }
    }
}
