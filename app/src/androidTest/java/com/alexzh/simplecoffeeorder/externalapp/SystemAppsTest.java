package com.alexzh.simplecoffeeorder.externalapp;

import android.content.Context;
import android.content.Intent;

import androidx.test.InstrumentationRegistry;
import androidx.test.filters.SdkSuppress;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class SystemAppsTest {
    private static final int TIMEOUT = 1000;
    private UiDevice mDevice;

    @Before
    public void setup() throws UiObjectNotFoundException {
        mDevice = UiDevice.getInstance(getInstrumentation());
        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                TIMEOUT);
    }


    @Test
    public void shouldSumTwoNumbers() throws UiObjectNotFoundException {
        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage("com.android.calculator2");
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg("com.android.calculator2").depth(0)),
                TIMEOUT);

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
        UiObject2 result = mDevice.findObject(By.res("com.android.calculator2", "result"));

        //verify result
        assertEquals("5", result.getText());
    }

    @Test
    public void shouldVerifyBatteryNotification() throws UiObjectNotFoundException {
        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage("com.android.settings");
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg("com.android.settings").depth(0)),
                TIMEOUT);

        UiScrollable settingList = new UiScrollable(new UiSelector().scrollable(true));
        settingList.scrollTextIntoView("Battery");

        // This will throw an exception on devices which don't have "Battery saver" text
        try {
            mDevice.findObject(By.text("Battery")).click();

            mDevice.wait(Until.hasObject(By.desc("Battery saver")), TIMEOUT);
            mDevice.findObject(By.text("Battery saver")).click();

            mDevice.wait(Until.hasObject(By.text("Off")), TIMEOUT);
            UiObject2 status = mDevice.findObject(By.text("Off"));
            assertEquals("Off", status.getText());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        mDevice.pressBack();
    }
}
