package com.example.dayout_organizer.helpers.view;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Counter {

    public static void CountTime(TextView _tv, View view,long duration) {
         new CountDownTimer(duration, 1000) {

            public void onTick(long millisUntilFinished) {
                _tv.setVisibility(View.VISIBLE);
                _tv.setText(new SimpleDateFormat("mm:ss", Locale.ENGLISH).format(new Date(millisUntilFinished)));
            }

            public void onFinish() {
                _tv.setVisibility(View.INVISIBLE);
                view.setVisibility(View.VISIBLE);
            }
        }.start();
    }

}
