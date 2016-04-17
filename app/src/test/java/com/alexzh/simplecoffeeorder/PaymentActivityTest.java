package com.alexzh.simplecoffeeorder;

import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class PaymentActivityTest {
    private static final float TOTAL_PRICE = 15.0f;

    private PaymentActivity mActivity;
    private TextView mTotalPrice;

    @Before
    public void setUp() {
        mActivity = Robolectric.buildActivity(PaymentActivity.class)
                .withIntent(PaymentActivity.createIntent(RuntimeEnvironment.application, TOTAL_PRICE))
                .create()
                .start()
                .resume()
                .visible()
                .get();

        mTotalPrice = (TextView) mActivity.findViewById(R.id.payment_data);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(mActivity);
    }

    @Test
    public void shouldHaveTotalPrice() {
        assertNotNull(mTotalPrice);
        assertEquals(View.VISIBLE, mTotalPrice.getVisibility());
        assertEquals(mActivity.getString(R.string.total_price, TOTAL_PRICE), mTotalPrice.getText());
    }
}
