package com.alexzh.simplecoffeeorder.ui.other;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CalculatorAppTest {

    private UiDevice mDevice;

    @Before
    public void setup() throws UiObjectNotFoundException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();

        UiObject allApps = mDevice.findObject(new UiSelector().description("Apps"));
        allApps.click();

        UiObject calculatorApp = mDevice.findObject(new UiSelector().description("Calculator"));
        calculatorApp.click();
    }

    @Test
    public void shouldSumTwoNumbers() throws UiObjectNotFoundException {
        mDevice.wait(Until.hasObject(By.text("5")), 3000);

        UiObject2 two = mDevice.findObject(By.text("2"));
        UiObject2 three = mDevice.findObject(By.text("3"));
        UiObject2 plus = mDevice.findObject(By.text("+"));
        UiObject2 equal = mDevice.findObject(By.text("="));

        two.click();
        plus.click();
        three.click();
        equal.click();
        UiObject2 result = mDevice.findObject(By.res("com.android.calculator2", "formula"));
        assertEquals("5", result.getText());
    }

    @After
    public void tearDown() {
        mDevice.pressBack();
    }

}
