package com.alexzh.simplecoffeeorder.actions;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.TypeTextAction;
import android.view.View;
import android.widget.TextView;

import com.alexzh.simplecoffeeorder.R;

import org.hamcrest.Matcher;
import org.w3c.dom.Text;

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
}
