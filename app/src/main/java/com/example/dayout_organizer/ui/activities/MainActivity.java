package com.example.dayout_organizer.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.ui.fragments.drawer.DrawerFragment;
import com.example.dayout_organizer.ui.fragments.home.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;


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

    private boolean isDrawerOpen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        FN.addFixedNameFadeFragment(MAIN_FRC, this, new HomeFragment());
    }

    private void initView() {
        createPollButton.setOnClickListener(onCreatePollClicked);
        createTripButton.setOnClickListener(onCreateTripClicked);
        drawerButton.setOnClickListener(onDrawerClicked);
        profileButton.setOnClickListener(onProfileClicked);
    }

    private final View.OnClickListener onCreatePollClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private final View.OnClickListener onCreateTripClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private final View.OnClickListener onDrawerClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideBottomBar();
            FN.addSlideLRFragmentUpFragment(MAIN_FRC, MainActivity.this, new DrawerFragment(), "drawer");
            isDrawerOpen = !isDrawerOpen;
        }
    };
    private final View.OnClickListener onProfileClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public void showBottomBar() {
        drawerButton.setVisibility(View.VISIBLE);
        bottomBar.setVisibility(View.VISIBLE);
        drawerButton.animate().setDuration(400).alpha(1);
        bottomBar.animate().setDuration(400).alpha(1);
    }

    private void hideBottomBar() {
        drawerButton.animate().setDuration(400).alpha(0);
        bottomBar.animate().setDuration(400).alpha(0);
        new Handler(getMainLooper()).postDelayed(() -> {
            drawerButton.setVisibility(View.GONE);
            bottomBar.setVisibility(View.GONE);
        }, 450);

    }


}
