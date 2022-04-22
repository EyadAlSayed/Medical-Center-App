package com.example.dayout_organizer.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSharedPreferences {

    public static SharedPreferences sp;
    public static SharedPreferences.Editor spEdit;


    @SuppressLint({"applyPrefEdits", "CommitPrefEdits"})
    public static void InitSharedPreferences(Context context) {
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
            spEdit = sp.edit();
        }
    }
}
