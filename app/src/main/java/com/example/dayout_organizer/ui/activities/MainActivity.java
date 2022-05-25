package com.example.dayout_organizer.ui.activities;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.config.AppSharedPreferences;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.room.popularPlaceRoom.Interfaces.IPopularPlaces;
import com.example.dayout_organizer.models.room.popularPlaceRoom.databases.PopularPlaceDataBase;
import com.example.dayout_organizer.ui.fragments.drawer.DrawerFragment;
import com.example.dayout_organizer.ui.fragments.home.HomeFragment;
import com.example.dayout_organizer.ui.fragments.profile.ProfileFragment;
import com.example.dayout_organizer.ui.fragments.trips.CreateTrip.CreateImageTripFragment;
import com.example.dayout_organizer.ui.fragments.trips.CreateTrip.CreateTripFragment;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;
import static com.example.dayout_organizer.config.AppSharedPreferences.CACHE_LAN;
import static com.example.dayout_organizer.config.AppSharedPreferences.GET_ACC_TOKEN;
import static com.example.dayout_organizer.config.AppSharedPreferences.InitSharedPreferences;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.drawer_btn)
    ImageButton drawerButton;
    @BindView(R.id.profile_btn)
    ImageButton profileButton;
    @BindView(R.id.bottom_bar)
    CardView bottomBar;

    @BindView(R.id.create_trip_btn)
    LinearLayout createTripButton;
    @BindView(R.id.create_poll_btn)
    LinearLayout createPollButton;

    @BindView(R.id.main_fr_c)
    FragmentContainerView mainFrC;

    private boolean isDrawerOpen = false;
    public IPopularPlaces roomPopularPlaces;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initRoomDB();


        InitSharedPreferences(this);

        if (AppSharedPreferences.GET_CACHE_LAN().equals("ar")) changeLanguage("ar", false);
        else if (AppSharedPreferences.GET_CACHE_LAN().equals("en")) changeLanguage("en", false);

        FN.addFixedNameFadeFragment(MAIN_FRC, this, new HomeFragment());

        Log.e("ACC_TOKEN", "onCreate: " + GET_ACC_TOKEN());
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fr_c);
        if (currentFragment instanceof HomeFragment) finish();
        else super.onBackPressed();
    }

    private void initRoomDB() {
        roomPopularPlaces = PopularPlaceDataBase.getINSTANCE(this).iPopularPlaces();
    }

    private void initView() {
        createPollButton.setOnClickListener(onCreatePollClicked);
        createTripButton.setOnClickListener(onCreateTripClicked);
        drawerButton.setOnClickListener(onDrawerClicked);
        profileButton.setOnClickListener(onProfileClicked);
    }

    private final View.OnClickListener onCreatePollClicked = v -> {
        NoteMessage.showSnackBar(MainActivity.this, "onCreatePollClicked");
        //TODO - EYAD
    };

    private final View.OnClickListener onCreateTripClicked = v -> {
        FN.addFixedNameFadeFragment(MAIN_FRC, MainActivity.this, new CreateTripFragment());

    };

    private final View.OnClickListener onDrawerClicked = v -> {
        FN.addSlideLRFragmentUpFragment(MAIN_FRC, MainActivity.this, new DrawerFragment(), "drawer");
        isDrawerOpen = !isDrawerOpen;
    };
    private final View.OnClickListener onProfileClicked = v -> FN.addFixedNameFadeFragment(MAIN_FRC, MainActivity.this, new ProfileFragment());

    public void showBottomBar() {
        bottomBar.setVisibility(View.VISIBLE);
        bottomBar.animate().setDuration(400).alpha(1);
    }

    public void hideBottomBar() {
        bottomBar.animate().setDuration(400).alpha(0);
        new Handler(getMainLooper()).postDelayed(() -> {
            bottomBar.setVisibility(View.GONE);
        }, 450);
    }

    public void showDrawerButton() {
        drawerButton.setEnabled(true);
        drawerButton.setVisibility(View.VISIBLE);
        drawerButton.animate().setDuration(400).alpha(1);
    }

    public void hideDrawerButton() {
        drawerButton.setEnabled(false);
        drawerButton.animate().setDuration(400).alpha(0);
        new Handler(getMainLooper()).postDelayed(() -> {
            drawerButton.setVisibility(View.GONE);
        }, 450);
    }

    private void refreshActivity() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(getIntent());
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void changeLanguage(String lang, boolean refresh) {
        Resources resources = this.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = new Locale(lang.toLowerCase());
        Locale.setDefault(locale);
        config.setLocale(locale);
        resources.updateConfiguration(config, displayMetrics);
        if (refresh)
            refreshActivity();
        CACHE_LAN(lang);
    }


}
