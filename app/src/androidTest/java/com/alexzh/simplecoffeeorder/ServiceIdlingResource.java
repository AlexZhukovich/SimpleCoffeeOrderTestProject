package com.alexzh.simplecoffeeorder;

import android.app.ActivityManager;
import android.content.Context;

import androidx.test.espresso.IdlingResource;

public class ServiceIdlingResource implements IdlingResource {
    private final Context mContext;
    private ResourceCallback mCallback;

    public ServiceIdlingResource(Context context) {
        this.mContext = context;
    }

    @Override
    public String getName() {
        return ServiceIdlingResource.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = !isIntentServiceRunning();
        if (idle && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mCallback = callback;
    }

    private boolean isIntentServiceRunning() {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo info : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (CoffeeService.class.getName().equals(info.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

