package com.alexzh.simplecoffeeorder.ui;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.activity.CoffeeOrderDetailsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.TreeMap;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class CoffeeOrderDetailsActivityTest {
    private final static int ORDER_COFFEE_COUNT = 1;
    private final static int ESPRESSO_POSITION = 2;
    private final static Coffee ESPRESSO = CoffeeService.getCoffeeData().get(ESPRESSO_POSITION);
    private final static float TOTAL_PRICE = ESPRESSO.getPrice() * ORDER_COFFEE_COUNT;
    private final static String NOTIFICATION_TITLE = "Coffee order app";
    private final static String NOTIFICATION_TEXT = "Thank you for your payment.";
    private final static String DELIVERY_INFO = "Alex";

    private final static int TIMEOUT = 3000;

    @Rule
    public ActivityTestRule<CoffeeOrderDetailsActivity> mActivityRule =
            new ActivityTestRule<>(CoffeeOrderDetailsActivity.class, true, false);

    @Test
    public void shouldOpenOrderReport() {
        TreeMap<Coffee, Integer> coffeeOrderMap = new TreeMap<>();
        coffeeOrderMap.put(ESPRESSO, ORDER_COFFEE_COUNT);

        Context context = InstrumentationRegistry.getTargetContext();
        Intent intent = CoffeeOrderDetailsActivity.createIntent(context, coffeeOrderMap, DELIVERY_INFO);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.total_price_toolbar))
                .check(matches(withText(context.getString(R.string.price, TOTAL_PRICE))));

        onView(withId(R.id.delivery_info))
                .check(matches(withText(context.getString(R.string.deliver_to_username, DELIVERY_INFO))))
                .check(matches(not(isEnabled())));

        onData(anything())
                .atPosition(0)
                .onChildView(withId(R.id.coffee_name)).check(matches(withText(ESPRESSO.getName())));
    }

    @Test
    public void shouldOpenOrderDetailInfo() {
        TreeMap<Coffee, Integer> coffeeOrderMap = new TreeMap<>();
        coffeeOrderMap.put(ESPRESSO, ORDER_COFFEE_COUNT);

        Context context = InstrumentationRegistry.getTargetContext();
        Intent intent = CoffeeOrderDetailsActivity.createIntent(context, coffeeOrderMap, null);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.total_price_toolbar))
                .check(matches(withText(context.getString(R.string.price, TOTAL_PRICE))));

        onView(withId(R.id.delivery_info))
                .check(matches(withText(isEmptyOrNullString())))
                .check(matches(isEnabled()));

        onData(anything())
                .atPosition(0)
                .onChildView(withId(R.id.coffee_name)).check(matches(withText(ESPRESSO.getName())));
    }

    @Test
    public void shouldOrderCoffeeAndVerifyNotification() {
        TreeMap<Coffee, Integer> coffeeOrderMap = new TreeMap<>();
        coffeeOrderMap.put(ESPRESSO, ORDER_COFFEE_COUNT);

        Context context = InstrumentationRegistry.getTargetContext();
        Intent intent = CoffeeOrderDetailsActivity.createIntent(context, coffeeOrderMap, null);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.total_price_toolbar))
                .check(matches(withText(context.getString(R.string.price, TOTAL_PRICE))));

        onView(withId(R.id.delivery_info))
                .perform(typeText(DELIVERY_INFO), closeSoftKeyboard());

        verifyCoffeeOrder();

        onView(withId(R.id.pay))
                .perform(click());

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        device.wait(Until.hasObject(By.text(NOTIFICATION_TITLE)), TIMEOUT);
        UiObject2 title = device.findObject(By.text(NOTIFICATION_TITLE));
        UiObject2 text = device.findObject(By.text(NOTIFICATION_TEXT));
        assertEquals(NOTIFICATION_TITLE, title.getText());
        assertEquals(NOTIFICATION_TEXT, text.getText());
        title.click();

        device.wait(Until.hasObject(By.text(ESPRESSO.getName())), TIMEOUT);

        onView(withId(R.id.delivery_info))
                .check(matches(withText(mActivityRule.getActivity().getString(R.string.deliver_to_username, DELIVERY_INFO))));

        verifyCoffeeOrder();
    }

    private void verifyCoffeeOrder() {
        onData(anything())
                .atPosition(0)
                .onChildView(withId(R.id.coffee_name)).check(matches(withText(ESPRESSO.getName())));

        onData(anything())
                .atPosition(0)
                .onChildView(withId(R.id.coffee_count)).check(matches(withText(String.valueOf(ORDER_COFFEE_COUNT))));
    }

    @Test
    public void shouldEnterUsername() {
        final TreeMap<Coffee, Integer> coffeeOrderMap = new TreeMap<>();
        coffeeOrderMap.put(ESPRESSO, ORDER_COFFEE_COUNT);

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = CoffeeOrderDetailsActivity.createIntent(targetContext, coffeeOrderMap, null);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.delivery_info))
                .perform(typeText(DELIVERY_INFO), closeSoftKeyboard());

        onView(withId(R.id.delivery_info))
                .check(matches(withText(DELIVERY_INFO)));
    }
}
