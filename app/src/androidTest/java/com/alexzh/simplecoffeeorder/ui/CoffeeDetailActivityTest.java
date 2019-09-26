package com.alexzh.simplecoffeeorder.ui;

import android.content.Context;
import android.content.Intent;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.alexzh.simplecoffeeorder.CoffeeService;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.utils.CoffeeUtils;
import com.alexzh.simplecoffeeorder.view.activity.CoffeeDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
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
        String ingredients = CoffeeUtils.getIngredientsString(espresso);
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = CoffeeDetailActivity.createCoffeeDetailIntent(targetContext, espresso);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.anim_toolbar))
                .check(matches(withToolbarTitle(is(espresso.getName()))));

        onView(withId(R.id.recyclerView))
                .check(matches(atPosition(0, is(targetContext.getString(R.string.ingredients)), is(ingredients))));

        onView(withId(R.id.recyclerView))
                .check(matches(atPosition(1, is(targetContext.getString(R.string.price_label)), is(targetContext.getString(R.string.price, espresso.getPrice())))));
    }
}
