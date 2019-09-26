package com.alexzh.simplecoffeeorder.utils;

import androidx.test.rule.ActivityTestRule;

public class StringUtils {

    public static String getString(ActivityTestRule rule, int resId) {
        return rule.getActivity().getString(resId);
    }

    public static String getString(ActivityTestRule rule, int resId, Object... args) {
        return rule.getActivity().getString(resId, args);
    }
}
