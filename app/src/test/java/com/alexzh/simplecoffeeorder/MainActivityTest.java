package com.alexzh.simplecoffeeorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    private MainActivity mActivity;
    private TextView mCoffeePrice;
    private TextView mTotalPrice;
    private Button mIncrementButton;
    private Button mDecrementButton;
    private TextView mCoffeeCount;
    private Button mPayButton;
    private Context mContext;


    @Before
    public void setUp() {
        mContext = RuntimeEnvironment.application.getApplicationContext();
        mActivity = Robolectric.setupActivity(MainActivity.class);

        mCoffeePrice = (TextView) mActivity.findViewById(R.id.coffee_price);
        mTotalPrice = (TextView) mActivity.findViewById(R.id.total_price);
        mIncrementButton = (Button) mActivity.findViewById(R.id.coffee_increment);
        mDecrementButton = (Button) mActivity.findViewById(R.id.coffee_decrement);
        mCoffeeCount = (TextView) mActivity.findViewById(R.id.coffee_count);
        mPayButton = (Button) mActivity.findViewById(R.id.pay);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(mActivity);
    }

    @Test
    public void shouldHavePriceLabels() {
        assertNotNull(mCoffeePrice);
        assertEquals(View.VISIBLE, mCoffeePrice.getVisibility());
        assertEquals(mActivity.getString(R.string.coffee_price, 5.0f), mCoffeePrice.getText());

        assertNotNull(mTotalPrice);
        assertEquals(View.VISIBLE, mTotalPrice.getVisibility());
        assertEquals(mActivity.getString(R.string.total_price, 0.0f), mTotalPrice.getText());
    }

    @Test
    public void shouldHaveCoffeeCupPicker() {
        assertNotNull(mIncrementButton);
        assertEquals(View.VISIBLE, mIncrementButton.getVisibility());

        assertNotNull(mDecrementButton);
        assertEquals(View.VISIBLE, mDecrementButton.getVisibility());

        assertNotNull(mCoffeeCount);
        assertEquals(View.VISIBLE, mCoffeeCount.getVisibility());
        assertEquals(mActivity.getString(R.string.default_coffee_count), mCoffeeCount.getText());
    }

    @Test
    public void shouldChangeCoffeeCupCountOnIncrementAndDecrementButtons() {
        int count = 0;

        assertNotNull(mCoffeeCount);
        assertEquals(View.VISIBLE, mCoffeeCount.getVisibility());
        assertEquals(String.valueOf(count), mCoffeeCount.getText());

        assertNotNull(mIncrementButton);
        assertEquals(View.VISIBLE, mIncrementButton.getVisibility());

        assertNotNull(mDecrementButton);
        assertEquals(View.VISIBLE, mDecrementButton.getVisibility());

        mIncrementButton.performClick();
        assertEquals(String.valueOf(++count), mCoffeeCount.getText());

        mDecrementButton.performClick();
        assertEquals(String.valueOf(--count), mCoffeeCount.getText());

        //Should be previous value because count can be just positive
        mDecrementButton.performClick();
        assertEquals(String.valueOf(count), mCoffeeCount.getText());
    }

    @Test
    public void shouldHavePayButton() {
        assertNotNull(mPayButton);
        assertEquals(View.VISIBLE, mPayButton.getVisibility());
        assertEquals(mActivity.getString(R.string.pay), mPayButton.getText());
    }

    @Test
    public void shouldBeCreatedIntent() {
        final float TEST_PRICE = 12.0f;

        Intent intent = PaymentActivity.createIntent(mContext, TEST_PRICE);
        assertNotNull(intent);
        Bundle bundle = intent.getExtras();
        assertEquals(TEST_PRICE, bundle.getFloat(PaymentActivity.TOTAL_PRICE), 0.00001f);
    }

    @Test
    public void shouldStartActivityOnPayButton() {
        assertNotNull(mPayButton);
        mPayButton.performClick();

        ShadowActivity shadowActivity = shadowOf(mActivity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(PaymentActivity.class.getName(),
                shadowIntent.getComponent().getClassName());
    }
}
