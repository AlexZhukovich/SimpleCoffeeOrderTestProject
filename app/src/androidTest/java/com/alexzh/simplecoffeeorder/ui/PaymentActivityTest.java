package com.alexzh.simplecoffeeorder.ui;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.activity.PaymentActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.alexzh.simplecoffeeorder.utils.StringUtils.getString;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class PaymentActivityTest {

    private List<Coffee> mCoffeeList;

    @Rule
    public ActivityTestRule<PaymentActivity> mActivityRule =
            new ActivityTestRule<>(PaymentActivity.class, true, false);

    @Before
    public void setup() {
        mCoffeeList = CoffeeService.getCoffeeData();
    }

    @Test
    public void shouldOpenDetailActivity() {
        float totalPrice = 0.0f;
        final HashMap<Coffee, Integer> coffeeOrderMap = new HashMap<>();
        coffeeOrderMap.put(mCoffeeList.get(0), 12);
        totalPrice += (12 * mCoffeeList.get(0).getPrice());

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = PaymentActivity.createIntent(targetContext, coffeeOrderMap);
        mActivityRule.launchActivity(intent);

        onData(anything()).atPosition(0).onChildView(withId(R.id.coffee_name)).check(matches(withText("Espresso")));
        onView(withId(R.id.total_price_toolbar)).check(matches(withText(getString(mActivityRule, R.string.price, totalPrice))));
    }
}
