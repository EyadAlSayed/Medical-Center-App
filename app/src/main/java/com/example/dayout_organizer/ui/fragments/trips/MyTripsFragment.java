package com.example.dayout_organizer.ui.fragments.trips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.MyTripsAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

@SuppressLint("NonConstantResourceId")
public class MyTripsFragment extends Fragment {

    View view;

    @Nullable
    @BindView(R.id.tabItem_active)
    TabItem tabItemActive;

    @Nullable
    @BindView(R.id.tabItem_upcoming)
    TabItem tabItemUpcoming;

    @Nullable
    @BindView(R.id.tabItem_history)
    TabItem tabItemHistory;

    @BindView(R.id.my_trips_tab_layout)
    TabLayout myTripsTabLayout;

    @BindView(R.id.my_trips_action_button)
    FloatingActionButton myTripsActionButton;

    @BindView(R.id.my_trips_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.my_trips_back_arrow)
    ImageButton myTripsBackArrow;

    @BindView(R.id.my_trips_filter)
    ImageButton myTripsFilter;

    MyTripsAdapter adapter;


    int type;

    List<TripModel> fakeTrips;
    List<TripModel> fakeFilteredList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_trips, container, false);
        ButterKnife.bind(this, view);
        tabItemActive = view.findViewById(R.id.tabItem_active);
        tabItemUpcoming = view.findViewById(R.id.tabItem_upcoming);
        tabItemHistory = view.findViewById(R.id.tabItem_history);
        myTripsTabLayout = view.findViewById(R.id.my_trips_tab_layout);
        initViews();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity) requireActivity()).hideBottomBar();
        super.onStart();
    }

    public MyTripsFragment() {
    }

    private void initViews() {

        fakeTrips = new ArrayList<>();
        fakeFilteredList = new ArrayList<>();
        List<String> stops = new ArrayList<>();
        stops.add("Jaramana");
        stops.add("Mashrou3");
        stops.add("Muhajreen");
        stops.add("Darya");
        TripModel trip1 = new TripModel("Trip1 Title", "Trip1 Description", 13, stops, stops, false, "Old", "Trip1 Date");
        TripModel trip2 = new TripModel("Trip2 Title", "Trip2 Description", 23, stops, stops, true, "Active", "Trip2 Date");
        TripModel trip3 = new TripModel("Trip3 Title", "Trip3 Description", 33, stops, stops, false, "Upcoming", "Trip3 Date");
        TripModel trip4 = new TripModel("Trip4 Title", "Trip4 Description", 43, stops, stops, true, "Old", "Trip4 Date");
        TripModel trip5 = new TripModel("Trip5 Title", "Trip5 Description", 53, stops, stops, false, "Upcoming", "Trip5 Date");
        fakeTrips.add(trip1);
        fakeTrips.add(trip2);
        fakeTrips.add(trip3);
        fakeTrips.add(trip4);
        fakeTrips.add(trip5);

        initRecycler();

        fakeFilteredList = filterList(fakeTrips, 3);
        for (TripModel model :
                fakeFilteredList) {
            System.out.println(model);
        }
        adapter.refreshList(fakeFilteredList, 3);

        myTripsTabLayout.addOnTabSelectedListener(tabListener);
        myTripsActionButton.setOnClickListener(onCreateTripClicked);
        myTripsBackArrow.setOnClickListener(onBackClicked);
        myTripsFilter.setOnClickListener(onFilterClicked);


    }

    private void initRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MyTripsAdapter(new ArrayList<>(), requireContext());
        recyclerView.setAdapter(adapter);
    }

    private List<TripModel> filterList(List<TripModel> list, int type) {

        List<TripModel> filteredList = new ArrayList<>();

        if (type == 1) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).state.equals("Old"))
                    filteredList.add(list.get(i));
            }
        } else if (type == 2) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).state.equals("Upcoming"))
                    filteredList.add(list.get(i));
            }
        } else if (type == 3) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).state.equals("Active")) {
                    filteredList.add(list.get(i));
                    break;
                }
            }
        }
        return filteredList;
    }

    private final TabLayout.OnTabSelectedListener tabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getPosition() == 0) {
                type = 3;
                fakeFilteredList = filterList(fakeTrips, type);
                adapter.refreshList(fakeFilteredList, type);
            } else if (tab.getPosition() == 1) {
                type = 2;
                fakeFilteredList = filterList(fakeTrips, type);
                adapter.refreshList(fakeFilteredList, type);
            } else if (tab.getPosition() == 2) {
                type = 1;
                fakeFilteredList = filterList(fakeTrips, type);
                adapter.refreshList(fakeFilteredList, type);
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private final View.OnClickListener onCreateTripClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: Go to create trip fragment. - Caesar.
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
            FN.addToStackSlideUDFragment(MAIN_FRC, requireActivity(), new FilterFragment(), "filter");
        }
    };
}