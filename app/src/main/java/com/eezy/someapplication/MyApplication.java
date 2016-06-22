package com.eezy.someapplication;

import android.app.Application;
import android.content.Context;

import java.util.Random;

public class MyApplication extends Application {

    public static Context context;
    public static Random random;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        random = new Random(System.currentTimeMillis());
    }
}
