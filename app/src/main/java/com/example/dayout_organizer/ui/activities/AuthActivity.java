package com.example.dayout_organizer.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.ui.fragments.auth.AuthFragment;

import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.AUTH_FRC;
import static com.example.dayout_organizer.config.AppSharedPreferences.InitSharedPreferences;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        ButterKnife.bind(this);
        InitSharedPreferences(this);
        FN.addFixedNameFadeFragment(AUTH_FRC, this, new AuthFragment());
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.auth_fr_c);
        if (currentFragment instanceof AuthFragment) finish();
        else super.onBackPressed();
    }
}
