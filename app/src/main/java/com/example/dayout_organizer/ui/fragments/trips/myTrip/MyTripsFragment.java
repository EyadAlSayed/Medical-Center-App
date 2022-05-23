package com.example.dayout_organizer.ui.fragments.trips.myTrip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.pager.MyTripPagerAdapter;
import com.example.dayout_organizer.adapter.recyclers.MyTripsAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.fragments.trips.CreateTrip.CreateTripFragment;
import com.example.dayout_organizer.ui.fragments.trips.FilterFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

@SuppressLint("NonConstantResourceId")
public class MyTripsFragment extends Fragment {

    View view;

    @BindView(R.id.my_trips_tab_layout)
    TabLayout myTripsTabLayout;

    @BindView(R.id.my_trips_action_button)
    FloatingActionButton myTripsActionButton;

    @BindView(R.id.my_trips_back_arrow)
    ImageButton myTripsBackArrow;

    @BindView(R.id.my_trips_filter)
    ImageButton myTripsFilter;

    @BindView(R.id.my_trips_view_pager)
    ViewPager myTripsViewPager;

    MyTripsAdapter adapter;

    // = 3 because when 'MyTrips' is first opened, it is set to 'Active' tab.
    int type = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_trips, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity) requireActivity()).hideBottomBar();
        super.onStart();
    }

    public MyTripsFragment() {}

    private void initTabLayout() {
        MyTripPagerAdapter pagerAdapter = new MyTripPagerAdapter(requireActivity().getSupportFragmentManager());
        pagerAdapter.addFragment(new ActiveTripFragment(adapter), "ACTIVE");
        pagerAdapter.addFragment(new UpComingTripFragment(adapter), "UPCOMING");
        pagerAdapter.addFragment(new OldTripFragment(adapter), "HISTORY");
        myTripsViewPager.setAdapter(pagerAdapter);
        myTripsTabLayout.setupWithViewPager(myTripsViewPager);
        myTripsTabLayout.addOnTabSelectedListener(onTabSelectedListener);
    }

    private void initViews() {
        adapter = new MyTripsAdapter(new ArrayList<>(),requireContext());
        initTabLayout();

        myTripsActionButton.setOnClickListener(onCreateTripClicked);
        myTripsBackArrow.setOnClickListener(onBackClicked);
        myTripsFilter.setOnClickListener(onFilterClicked);
    }


    private final View.OnClickListener onCreateTripClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.addFixedNameFadeFragment(MAIN_FRC,requireActivity(),new CreateTripFragment());
        }
    };

    private final View.OnClickListener onBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.popStack(requireActivity());
        }
    };

    private final View.OnClickListener onFilterClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FilterFragment.isFilterOpen = true;
            FN.addToStackSlideUDFragment(MAIN_FRC, requireActivity(), new FilterFragment(adapter,type), "filter");
        }
    };

    private final TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getPosition() == 0) type = 3;
            else if(tab.getPosition() == 1) type = 2;
            else if(tab.getPosition() == 2) type = 1;
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
}