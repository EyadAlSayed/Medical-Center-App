package com.example.dayout_organizer.helpers.system;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class InputHelper {

    public static void hideKeyboard(Activity activity){
        InputMethodManager inputMethodManager =(InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
