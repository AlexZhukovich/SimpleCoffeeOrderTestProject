package com.alexzh.simplecoffeeorder.actions;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class RecyclerChildViewActions {

    public static ViewAction clickByChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child of a view with id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                if (v != null) {
                    v.performClick();
                }
            }
        };
    }

    public static ViewAction checkTextViewByChildViewWithId(final int id, final String text) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child of a view with id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView v = (TextView) view.findViewById(id);
                if (v != null) {
                    assertEquals(text, v.getText().toString());
                }
            }
        };
    }

    public static void clickToViewChildItem(int recyclerViewId, String item, int childId) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(item)),
                        RecyclerChildViewActions.clickByChildViewWithId(childId)));
    }

    public static void checkTextViewCountForCoffee(int recyclerViewId, int childId, String coffee, String count) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(coffee)),
                        RecyclerChildViewActions.checkTextViewByChildViewWithId(childId, count)));
    }
}
