package com.eezy.someapplication.util;

import android.app.Activity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

import com.eezy.someapplication.MyApplication;

public class DeviceUtil {

    public static Point getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = MyApplication.context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
