package com.example.dayout_organizer.config;

import android.util.Log;
import android.util.Patterns;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.models.error.ErrorModel;
import com.google.gson.Gson;

import java.util.regex.Pattern;

public class AppConstants {

    public final static  int AUTH_FRC = R.id.auth_fr_c;
    public final static  int MAIN_FRC = R.id.main_fr_c;

    public final static Pattern EN_NAME_REGEX = Pattern.compile("[a-zA-Z]([a-zA-Z]+| )*");
    public final static Pattern AR_NAME_REGEX = Pattern.compile("[a-zA-Z]([a-zA-Z]+| )*"); // Pattern.compile("\\p{InArabic}");

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

    //  place keys
    public static final String PLACE_DB = "place_database";
    public static final String PLACE_TABLE = "place_table";
    public static final String PLACE_DETAILS_TABLE = "place_details_table";
    public static final String PLACE_DATA = "place_data";
    public static final String PLACE_PHOTO = "place_photo";


    // profile keys
    public static final String PROFILE_DB = "profile_database";
    public static final String PROFILE_TABLE = "profile_table";
    public static final String PROFILE_DATA = "profile_data";
    public static final String PROFILE_USER = "profile_user";


    // notification keys
    public static final String NOTIFICATION_DB = "notification_database";
    public static final String NOTIFICATION_TABLE = "notification_table";
    public static final String NOTIFICATION_DATA = "notification_data";


    // passengers keys
    public static final String PASSENGERS_DB = "passengers_database";
    public static final String PASSENGERS_TABLE = "passengers_table";
    public static final String PASSENGERS_DATA = "passengers_data";
    public static final String PASSENGERS_BOOKED_FOR = "passengers_booked_for";


    // poll keys
    public static final String POLL_DB = "poll_database";
    public static final String POLL_TABLE = "poll_table";
    public static final String POLL_DATA = "poll_data";
    public static final String POLL_CHOICE = "poll_choice";

}
