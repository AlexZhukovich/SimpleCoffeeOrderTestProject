package com.alexzh.simplecoffeeorder.ui;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.view.activity.CoffeeDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.alexzh.simplecoffeeorder.matchers.RecyclerViewMatcher.atPosition;
import static com.alexzh.simplecoffeeorder.matchers.ToolbarMatcher.withToolbarTitle;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
public class CoffeeDetailActivityTest {

    @Rule
    public ActivityTestRule<CoffeeDetailActivity> mActivityRule =
            new ActivityTestRule<>(CoffeeDetailActivity.class, true, false);

    @Test
    public void shouldCheckIntentValues() {
        Coffee espresso = CoffeeService.getCoffeeData().get(0);
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = CoffeeDetailActivity.createCoffeeDetailIntent(targetContext, espresso);
        mActivityRule.launchActivity(intent);

        StringBuilder ingredients = new StringBuilder();
        for (int i = 0; i < espresso.getIngredients().length; i++) {
            ingredients.append(espresso.getIngredients()[i]);
            if (i != espresso.getIngredients().length - 1) {
                ingredients.append(",\n");
            }
        }

        onView(withId(R.id.anim_toolbar))
                .check(matches(withToolbarTitle(is(espresso.getName()))));

        onView(withId(R.id.recyclerView))
                .check(matches(atPosition(0, targetContext.getString(R.string.ingredients), ingredients.toString())));

        onView(withId(R.id.recyclerView))
                .check(matches(atPosition(1, targetContext.getString(R.string.price_label), targetContext.getString(R.string.price, espresso.getPrice()))));
    }
}
