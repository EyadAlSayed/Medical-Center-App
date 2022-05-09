package com.example.dayout_organizer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.config.AppSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppSharedPreferences.GET_ACC_TOKEN;
import static com.example.dayout_organizer.config.AppSharedPreferences.IS_REMEMBER_ME;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.splash_motto)
    TextView splashMotto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);
        AppSharedPreferences.InitSharedPreferences(this);
        initView();
    }

    private void applyAnimation() {
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation bottom_up = AnimationUtils.loadAnimation(this, R.anim.down_to_up_animation);
        fade_in.setDuration(2000);
        bottom_up.setDuration(2000);
        splashMotto.startAnimation(fade_in);
        splashMotto.startAnimation(bottom_up);
    }


    private void initView() {
        applyAnimation();
        if (GET_ACC_TOKEN().isEmpty() && !IS_REMEMBER_ME()) openAuthActivity();
        else openMainActivity();
    }


    private void openMainActivity() {
        new Handler(getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 3000);
    }

    private void openAuthActivity() {
        new Handler(getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, AuthActivity.class));
            finish();
        }, 3000);
    }
}