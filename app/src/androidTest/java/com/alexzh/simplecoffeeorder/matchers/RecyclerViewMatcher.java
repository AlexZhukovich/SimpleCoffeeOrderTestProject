package com.alexzh.simplecoffeeorder.matchers;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.adapter.CoffeeOrderListAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RecyclerViewMatcher {

    public static Matcher<View> atPosition(final int position, final Matcher<String> title, final Matcher<String> description) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position: " + position + " with title: " + title + " and description: " + description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder != null) {
                    TextView titleTextView = viewHolder.itemView.findViewById(R.id.category_title);
                    TextView descTextView = viewHolder.itemView.findViewById(R.id.category_description);
                    return title.matches(titleTextView.getText().toString()) && description.matches(descTextView.getText().toString());
                }
                return false;
            }
        };
    }

    public static Matcher<View> atPosition(final int position, final Matcher<String> coffeeName, final int coffeeCount) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position: ");
                description.appendText(String.valueOf(position));
                description.appendText(" with coffee: ");
                description.appendText(coffeeName.toString());
                description.appendText(" and count: ");
                description.appendText(String.valueOf(coffeeCount));
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder != null) {
                    TextView coffeeTextView = viewHolder.itemView.findViewById(R.id.coffee_name);
                    TextView coffeeCountTextView = viewHolder.itemView.findViewById(R.id.coffee_count);
                    return coffeeName.matches(coffeeTextView.getText().toString())
                            && String.valueOf(coffeeCount).equals(coffeeCountTextView.getText().toString());
                }
                return false;
            }
        };
    }

    public static Matcher<RecyclerView.ViewHolder> withCoffeeNameAndCount(final Matcher<String> coffeeName, final int count) {
        return new BoundedMatcher<RecyclerView.ViewHolder, CoffeeOrderListAdapter.CoffeeViewHolder>(CoffeeOrderListAdapter.CoffeeViewHolder.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with coffeeName = " + coffeeName.toString());
            }

            @Override
            protected boolean matchesSafely(CoffeeOrderListAdapter.CoffeeViewHolder item) {
                if (item != null && item.mCoffeeName != null) {
                    return coffeeName.matches(item.mCoffeeName.getText().toString())
                            && count == item.mCoffeeCountPicker.getCoffeeCount();
                }
                return false;
            }
        };
    }

    public static Matcher<View> withCoffeeCount(final Matcher<String> coffeeCount) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with coffeeCount = " + coffeeCount.toString());
            }

            @Override
            protected boolean matchesSafely(RecyclerView view) {

                TextView textView = view.findViewById(R.id.coffee_count);
                if (textView != null) {
                    return coffeeCount.matches(textView.getText().toString());
                }
                return false;
            }
        };
    }
}
