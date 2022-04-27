package com.example.dayout_organizer.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.ui.fragments.drawer.DrawerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.explore_btn)
    ImageButton exploreButton;
    @BindView(R.id.favorite_place_btn)
    ImageButton favoritePlaceButton;
    @BindView(R.id.drawer_btn)
    ImageButton drawerButton;
    @BindView(R.id.profile_btn)
    ImageButton profileButton;
    @BindView(R.id.bottom_bar)
    CardView bottomBar;

    private boolean isDrawerOpen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        exploreButton.setOnClickListener(onExploreClicked);
        favoritePlaceButton.setOnClickListener(onFavoriteClicked);
        drawerButton.setOnClickListener(onDrawerClicked);
        profileButton.setOnClickListener(onProfileClicked);
    }

    private final View.OnClickListener onExploreClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private final View.OnClickListener onFavoriteClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private final View.OnClickListener onDrawerClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.addToStackSlideLRFragment(MAIN_FRC, MainActivity.this, new DrawerFragment(), "drawer");
            isDrawerOpen = !isDrawerOpen;
            hideBottomBar();
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
    }

    private void hideBottomBar() {
        drawerButton.setVisibility(View.GONE);
        bottomBar.setVisibility(View.GONE);
    }


}
