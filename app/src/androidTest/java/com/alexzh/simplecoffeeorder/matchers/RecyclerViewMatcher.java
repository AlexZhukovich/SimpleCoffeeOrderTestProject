package com.alexzh.simplecoffeeorder.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alexzh.simplecoffeeorder.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RecyclerViewMatcher {

    public static Matcher<View> atPosition(final int position, final String title, final String description) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position: " + position + " with title: " + title + " and description: " + description );
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder != null) {
                    TextView titleTextView = (TextView) viewHolder.itemView.findViewById(R.id.category_title);
                    TextView descTextView = (TextView) viewHolder.itemView.findViewById(R.id.category_description);
                    return title.equals(titleTextView.getText().toString()) && description.equals(descTextView.getText().toString());
                }
                return false;
            }
        };
    }
}
