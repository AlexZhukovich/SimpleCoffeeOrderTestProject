package com.alexzh.simplecoffeeorder.ui;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.ServiceIdlingResource;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.activity.CoffeeOrderListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.registerIdlingResources;
import static androidx.test.espresso.Espresso.unregisterIdlingResources;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.alexzh.simplecoffeeorder.actions.RecyclerChildViewActions.*;
import static com.alexzh.simplecoffeeorder.actions.RecyclerChildViewActions.checkTextViewByChildViewWithId;
import static com.alexzh.simplecoffeeorder.matchers.RecyclerViewMatcher.atPosition;
import static com.alexzh.simplecoffeeorder.utils.StringUtils.getString;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
public class CoffeeOrderListActivityTest {
    private List<Coffee> mCoffeeList;
    private ServiceIdlingResource mServiceIdlingResource;

    @Rule
    public ActivityTestRule<CoffeeOrderListActivity> mActivityRule =
            new ActivityTestRule<>(CoffeeOrderListActivity.class);

    @Before
    public void setup() {
        mCoffeeList = CoffeeService.getCoffeeData();

        mServiceIdlingResource =
                new ServiceIdlingResource(mActivityRule.getActivity().getApplicationContext());
        registerIdlingResources(mServiceIdlingResource);
    }

    @Test
    public void shouldDisplayCoffeeOrderList() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(mCoffeeList.size() - 1));

        for (Coffee coffee : mCoffeeList) {
            onView(withId(R.id.recyclerView))
                    .perform(RecyclerViewActions.actionOnItem(
                            hasDescendant(withText(coffee.getName())),
                            checkTextViewByChildViewWithId(R.id.coffee_count, "0")));
        }
    }

    @Test
    public void shouldOrderEspressoAndLatte() {
        int espressoCount = 0;
        int latteCount = 0;
        float totalCoffeePrice = 0.0f;
        final int espressoPosition = 2;
        final int lattePosition = 4;

        final Coffee espresso = mCoffeeList.get(espressoPosition);
        final Coffee latte = mCoffeeList.get(lattePosition);
        assertEquals("Espresso", espresso.getName());
        assertEquals("Latte", latte.getName());

        totalCoffeePrice += espresso.getPrice();
        orderCoffee(espresso.getName(), R.id.coffee_increment);
        verifyOrderedCoffee(espressoPosition, espresso.getName(), ++espressoCount, totalCoffeePrice);

        totalCoffeePrice += latte.getPrice();
        orderCoffee(latte.getName(), R.id.coffee_increment);
        verifyOrderedCoffee(lattePosition, latte.getName(), ++latteCount, totalCoffeePrice);
    }

    @After
    public void tearDown() {
        unregisterIdlingResources(mServiceIdlingResource);
    }

    private void orderCoffee(String coffeeName, int clickedViewId) {
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(coffeeName)),
                        clickByChildViewWithId(clickedViewId)));
    }

    private void verifyOrderedCoffee(int position, String coffeeName, int coffeeCount, float totalPrice) {
        onView(withId(R.id.recyclerView))
                .check(matches(atPosition(position, is(coffeeName), coffeeCount)));

        onView(withId(R.id.total_price_toolbar))
                .check(matches(withText(getString(mActivityRule, R.string.price, totalPrice))));

    }
}
