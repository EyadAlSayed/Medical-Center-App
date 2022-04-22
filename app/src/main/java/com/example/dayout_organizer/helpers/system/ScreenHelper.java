package com.example.dayout_organizer.helpers.system;

import android.app.Activity;
import android.view.WindowManager;

public class ScreenHelper {

    public static void hideStatusBar(Activity activity) {
        // Hide status bar
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void statusBarTransparent(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
