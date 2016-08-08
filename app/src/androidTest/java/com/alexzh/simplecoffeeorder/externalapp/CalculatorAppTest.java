package com.alexzh.simplecoffeeorder.externalapp;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class CalculatorAppTest {
    private static final int TIMEOUT = 1000;
    private UiDevice mDevice;

    @Before
    public void setup() throws UiObjectNotFoundException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();

        //open list of application
        mDevice.findObject(By.desc("Apps")).click();

        //open calculator application
        mDevice.wait(Until.hasObject(By.text("Calculator")), 3000);
        UiObject2 calculatorApp = mDevice.findObject(By.desc("Calculator"));
        calculatorApp.click();
    }

    @Test
    public void shouldSumTwoNumbers() throws UiObjectNotFoundException {
        mDevice.wait(Until.hasObject(By.text("5")), 3000);

        //create objects, which we will for emulating user behaviour
        UiObject2 two = mDevice.findObject(By.text("2"));
        UiObject2 three = mDevice.findObject(By.text("3"));
        UiObject2 plus = mDevice.findObject(By.text("+"));
        UiObject2 equal = mDevice.findObject(By.text("="));

        //sequence of emulating actions
        two.click();
        plus.click();
        three.click();
        equal.click();

        //get results
        UiObject2 result = mDevice.findObject(By.res("com.android.calculator2", "formula"));

        //verify result
        assertEquals("5", result.getText());
    }

    @Test
    public void shouldVerifyBatteryNotification() throws UiObjectNotFoundException {
        UiScrollable appViewsList = new UiScrollable(new UiSelector()
                .scrollable(true));

        appViewsList.scrollTextIntoView("Settings");
        mDevice.findObject(new UiSelector().description("Settings")).click();

        UiScrollable settingList = new UiScrollable(new UiSelector().scrollable(true));
        settingList.scrollTextIntoView("Battery");
        mDevice.findObject(By.text("Battery")).click();

        mDevice.wait(Until.hasObject(By.desc("More options")), TIMEOUT);
        mDevice.findObject(By.desc("More options")).click();

        mDevice.wait(Until.hasObject(By.desc("Battery saver")), TIMEOUT);
        mDevice.findObject(By.text("Battery saver")).click();

        mDevice.wait(Until.hasObject(By.text("OFF")), TIMEOUT);
        mDevice.findObject(By.text("OFF")).click();

        mDevice.openNotification();
        mDevice.wait(Until.hasObject(By.text("Battery saver is on")), TIMEOUT);
        UiObject2 notificationTitle = mDevice.findObject(By.text("Battery saver is on"));
        assertEquals(notificationTitle.getText(), "Battery saver is on");

        mDevice.findObject(By.text("Turn off battery saver")).click();

        mDevice.wait(Until.hasObject(By.text("OFF")), TIMEOUT);
        UiObject2 status = mDevice.findObject(By.text("OFF"));
        assertEquals("OFF", status.getText());
    }

    @After
    public void tearDown() {
        mDevice.pressBack();
    }
}
