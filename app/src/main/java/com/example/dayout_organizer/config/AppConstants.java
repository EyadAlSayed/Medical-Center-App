package com.example.dayout_organizer.config;

import android.util.Log;
import android.util.Patterns;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.models.Error.ErrorModel;
import com.google.gson.Gson;

import java.util.regex.Pattern;

public class AppConstants {

    public final static  int AUTH_FRC = R.id.auth_fr_c;
    public final static  int MAIN_FRC = R.id.main_fr_c;

    public final static Pattern NAME_REGEX = Pattern.compile("[a-zA-Z]([a-zA-Z]+| )*");
    public final static Pattern PHONE_NUMBER_REGEX = Pattern.compile("09\\d{8}");
    public final static Pattern EMAIL_REGEX = Patterns.EMAIL_ADDRESS;


    // shared preferences keys

    public static final String USER_ID = "user_id";
    public static final String ACC_TOKEN ="acc_token";
    public static final String REMEMBER_ME = "remember_me";
    public static final String LAN = "lan";

    // const function

    public static String getErrorMessage(String errorAsString) {
        try {
            Log.d("content", "getErrorMessage: " + errorAsString);
            Gson gson = new Gson();
            ErrorModel errorModel = gson.fromJson(errorAsString, ErrorModel.class);
            return errorModel.getMessage();
        } catch (Exception e) {
            return "Failed while reading the error message";
        }

    }

    // popular place keys
    public static final String POPULAR_PLACE_DB = "popularplace_database";
    public static final String POPULAR_PLACE_TABLE = "popularplace_table";
    public static final String POPULAR_PLACE_DATA = "popularplace_data";
    public static final String POPULAR_PLACE_PHOTO = "popularplace_photo";

    // profile keys
    public static final String PROFILE_DB = "profile_database";
    public static final String PROFILE_TABLE = "profile_table";
    public static final String PROFILE_DATA = "profile_data";
    public static final String PROFILE_USER = "profile_user";

}
