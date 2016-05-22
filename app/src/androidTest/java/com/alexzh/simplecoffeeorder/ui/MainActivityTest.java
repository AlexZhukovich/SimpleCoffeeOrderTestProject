package com.alexzh.simplecoffeeorder.ui;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.ServiceIdlingResource;
import com.alexzh.simplecoffeeorder.actions.RecyclerChildViewActions;
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

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private List<Coffee> mCoffeeList;
    private ServiceIdlingResource mServiceIdlingResource;

    @Before
    public void setup() {
        mCoffeeList = CoffeeService.getCoffeeData();

        mServiceIdlingResource =
                new ServiceIdlingResource(mActivityRule.getActivity().getApplicationContext());
        registerIdlingResources(mServiceIdlingResource);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldDisplayCoffeeOrderList() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(mCoffeeList.size() - 1));

        for (Coffee coffee : mCoffeeList) {
            onView(withId(R.id.recyclerView))
                    .perform(RecyclerViewActions.actionOnItem(
                            hasDescendant(withText(coffee.getName())),
                            RecyclerChildViewActions.checkTextViewByChildViewWithId(R.id.coffee_count, "0")));
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

        clickToChildItem(espresso, R.id.coffee_increment);
        checkCoffeeCountForCoffee(espresso, ++espressoCount);
        totalCoffeePrice += espressoPrice;
        onView(withId(R.id.total_price_toolbar)).check(matches(withText(mActivityRule.getActivity().getString(R.string.price, totalCoffeePrice))));

        clickToChildItem(espresso, R.id.coffee_increment);
        checkCoffeeCountForCoffee(espresso, ++espressoCount);
        totalCoffeePrice += espressoPrice;
        onView(withId(R.id.total_price_toolbar)).check(matches(withText(mActivityRule.getActivity().getString(R.string.price, totalCoffeePrice))));

        clickToChildItem(espresso, R.id.coffee_decrement);
        checkCoffeeCountForCoffee(espresso, --espressoCount);
        totalCoffeePrice -= espressoPrice;
        onView(withId(R.id.total_price_toolbar)).check(matches(withText(mActivityRule.getActivity().getString(R.string.price, totalCoffeePrice))));

        clickToChildItem(espresso, R.id.coffee_decrement);
        checkCoffeeCountForCoffee(espresso, --espressoCount);
        totalCoffeePrice -= espressoPrice;
        onView(withId(R.id.total_price_toolbar)).check(matches(withText(mActivityRule.getActivity().getString(R.string.price, totalCoffeePrice))));

        clickToChildItem(coffeeZoro, R.id.coffee_increment);
        checkCoffeeCountForCoffee(coffeeZoro, ++coffeeZoroCount);
        totalCoffeePrice += coffeeZoroPrice;
        onView(withId(R.id.total_price_toolbar)).check(matches(withText(mActivityRule.getActivity().getString(R.string.price, totalCoffeePrice))));
    }

    private void clickToChildItem(String item, int childId) {
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(item)),
                        RecyclerChildViewActions.clickByChildViewWithId(childId)));
    }

    private void checkCoffeeCountForCoffee(String coffee, int count) {
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(coffee)),
                        RecyclerChildViewActions.checkTextViewByChildViewWithId(R.id.coffee_count, String.valueOf(count))));
    }

    @After
    public void tearDown() {
        unregisterIdlingResources(mServiceIdlingResource);
    }
}
