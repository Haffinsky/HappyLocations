package com.haffa.happylocations.Utilities;

import android.app.Application;
import android.content.Context;

/**
 * Created by Peker on 11/25/2016.
 */

public class RetriveMyApplicationContext extends Application {

    private static RetriveMyApplicationContext mRetriveMyApplicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mRetriveMyApplicationContext = this;
    }

    public static RetriveMyApplicationContext getInstance() {
        return mRetriveMyApplicationContext;
    }

    public static Context getAppContext() {
        return mRetriveMyApplicationContext.getApplicationContext();
    }
}