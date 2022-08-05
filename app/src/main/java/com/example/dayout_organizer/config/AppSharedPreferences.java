package com.example.dayout_organizer.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.dayout_organizer.api.ApiClient.BASE_URL;
import static com.example.dayout_organizer.config.AppConstants.ACC_TOKEN;
import static com.example.dayout_organizer.config.AppConstants.LAN;
import static com.example.dayout_organizer.config.AppConstants.REMEMBER_ME;
import static com.example.dayout_organizer.config.AppConstants.USER_ID;

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

    public  static  void  CACHE_BASE_URL(String baseUrl){
        spEdit.putString("BASE_URL",baseUrl).apply();
    }
    public static String  GET_BASE_URL(){
        return sp.getString("BASE_URL",BASE_URL);
    }

    public static void REMOVE_ALL(){
        spEdit.clear().apply();
    }

    public static void CACHE_LAN(String lan) {
        spEdit.putString(LAN,lan).apply();
    }

    public static String GET_CACHE_LAN(){
        return sp.getString(LAN,"");
    }

    public static void CACHE_AUTH_DATA(int userId,String accToken){
        spEdit.putString(ACC_TOKEN,accToken).putInt(USER_ID,userId).apply();
    }

    public static String GET_ACC_TOKEN(){
        return sp.getString(ACC_TOKEN,"");
    }

    public static int GET_USER_ID(){
        return sp.getInt(USER_ID,0);
    }

    public static void CACHE_REMEMBER_ME(boolean isRemember){
        spEdit.putBoolean(REMEMBER_ME,isRemember).apply();
    }

    public static boolean IS_REMEMBER_ME(){
        return sp.getBoolean(REMEMBER_ME,false);
    }
}
