package com.alexzh.simplecoffeeorder.ui;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.activity.CoffeeOrderDetailsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.TreeMap;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class CoffeeOrderDetailsActivityTest {

    private List<Coffee> mCoffeeList;

    @Rule
    public ActivityTestRule<CoffeeOrderDetailsActivity> mActivityRule =
            new ActivityTestRule<>(CoffeeOrderDetailsActivity.class, true, false);

    @Before
    public void setup() {
        mCoffeeList = CoffeeService.getCoffeeData();
    }

    @Test
    public void shouldOpenOrderReport() {
        int coffeeCount = 1;
        Coffee espresso = CoffeeService.getCoffeeData().get(2);
        float totalPrice = espresso.getPrice() * coffeeCount;
        String deliveryInfo = "User";
        TreeMap<Coffee, Integer> coffeeOrderMap = new TreeMap<>();
        coffeeOrderMap.put(espresso, coffeeCount);

        Context context = InstrumentationRegistry.getTargetContext();
        Intent intent = CoffeeOrderDetailsActivity.createIntent(context, coffeeOrderMap, deliveryInfo);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.total_price_toolbar))
                .check(matches(withText(context.getString(R.string.price, totalPrice))));

        onView(withId(R.id.delivery_info))
                .check(matches(withText(context.getString(R.string.deliver_to_username, deliveryInfo))))
                .check(matches(not(isEnabled())));

        onData(anything())
                .atPosition(0)
                .onChildView(withId(R.id.coffee_name)).check(matches(withText(espresso.getName())));
    }

    @Test
    public void shouldOpenOrderDetailInfo() {
        int coffeeCount = 1;
        Coffee espresso = CoffeeService.getCoffeeData().get(2);
        float totalPrice = espresso.getPrice() * coffeeCount;
        String deliveryInfo = null;
        TreeMap<Coffee, Integer> coffeeOrderMap = new TreeMap<>();
        coffeeOrderMap.put(espresso, coffeeCount);

        Context context = InstrumentationRegistry.getTargetContext();
        Intent intent = CoffeeOrderDetailsActivity.createIntent(context, coffeeOrderMap, deliveryInfo);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.total_price_toolbar))
                .check(matches(withText(context.getString(R.string.price, totalPrice))));

        onView(withId(R.id.delivery_info))
                .check(matches(withText(isEmptyOrNullString())))
                .check(matches(isEnabled()));

        onData(anything())
                .atPosition(0)
                .onChildView(withId(R.id.coffee_name)).check(matches(withText(espresso.getName())));
    }

    @Test
    public void shouldEnterUsername() {
        final TreeMap<Coffee, Integer> coffeeOrderMap = new TreeMap<>();
        coffeeOrderMap.put(mCoffeeList.get(0), 12);

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = CoffeeOrderDetailsActivity.createIntent(targetContext, coffeeOrderMap, null);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.delivery_info))
                .perform(typeText("User"), closeSoftKeyboard());

        onView(withId(R.id.delivery_info))
                .check(matches(withText("User")));
    }
}
