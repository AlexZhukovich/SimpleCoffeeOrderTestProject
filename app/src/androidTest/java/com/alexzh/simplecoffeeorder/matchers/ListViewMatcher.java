package com.alexzh.simplecoffeeorder.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;

import com.alexzh.simplecoffeeorder.model.Coffee;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ListViewMatcher {

    public static Matcher<Object> withCoffee(final Coffee coffee) {
        return new BoundedMatcher<Object, Coffee>(Coffee.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("has value: " + coffee.toString());
            }

            @Override
            protected boolean matchesSafely(Coffee item) {
                return item.equals(coffee);
            }
        };
    }

}
