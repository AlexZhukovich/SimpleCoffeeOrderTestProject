package com.alexzh.simplecoffeeorder.ui;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.ServiceIdlingResource;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.activity.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.alexzh.simplecoffeeorder.actions.RecyclerChildViewActions.checkTextViewByChildViewWithId;
import static com.alexzh.simplecoffeeorder.actions.RecyclerChildViewActions.checkTextViewCountForCoffee;
import static com.alexzh.simplecoffeeorder.actions.RecyclerChildViewActions.clickToViewChildItem;
import static com.alexzh.simplecoffeeorder.utils.StringUtils.getString;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private List<Coffee> mCoffeeList;
    private ServiceIdlingResource mServiceIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

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
    public void shouldOrderEspressoAndCafeZorro() {
        int espressoCount = 0;
        int coffeeZoroCount = 0;
        float totalCoffeePrice = 0.0f;
        final List<Coffee> coffeeList = CoffeeService.getCoffeeData();
        final String espresso = coffeeList.get(0).getName();
        final float espressoPrice = coffeeList.get(0).getPrice();

        final String coffeeZoro = coffeeList.get(7).getName();
        final float coffeeZoroPrice = coffeeList.get(7).getPrice();

        clickToViewChildItem(R.id.recyclerView, espresso, R.id.coffee_increment);
        checkTextViewCountForCoffee(R.id.recyclerView, R.id.coffee_count, espresso, String.valueOf(++espressoCount));
        totalCoffeePrice += espressoPrice;
        onView(withId(R.id.total_price_toolbar)).check(matches(withText(getString(mActivityRule, R.string.price, totalCoffeePrice))));

        clickToViewChildItem(R.id.recyclerView, espresso, R.id.coffee_increment);
        checkTextViewCountForCoffee(R.id.recyclerView, R.id.coffee_count, espresso, String.valueOf(++espressoCount));
        totalCoffeePrice += espressoPrice;
        onView(withId(R.id.total_price_toolbar)).check(matches(withText(getString(mActivityRule, R.string.price, totalCoffeePrice))));

        clickToViewChildItem(R.id.recyclerView, espresso, R.id.coffee_decrement);
        checkTextViewCountForCoffee(R.id.recyclerView, R.id.coffee_count, espresso, String.valueOf(--espressoCount));
        totalCoffeePrice -= espressoPrice;
        onView(withId(R.id.total_price_toolbar)).check(matches(withText(getString(mActivityRule, R.string.price, totalCoffeePrice))));

        clickToViewChildItem(R.id.recyclerView, espresso, R.id.coffee_decrement);
        checkTextViewCountForCoffee(R.id.recyclerView, R.id.coffee_count, espresso, String.valueOf(--espressoCount));
        totalCoffeePrice -= espressoPrice;
        onView(withId(R.id.total_price_toolbar)).check(matches(withText(getString(mActivityRule, R.string.price, totalCoffeePrice))));

        clickToViewChildItem(R.id.recyclerView, coffeeZoro, R.id.coffee_increment);
        checkTextViewCountForCoffee(R.id.recyclerView, R.id.coffee_count, coffeeZoro, String.valueOf(++coffeeZoroCount));
        totalCoffeePrice += coffeeZoroPrice;
        onView(withId(R.id.total_price_toolbar)).check(matches(withText(getString(mActivityRule, R.string.price, totalCoffeePrice))));
    }

    @After
    public void tearDown() {
        unregisterIdlingResources(mServiceIdlingResource);
    }
}
