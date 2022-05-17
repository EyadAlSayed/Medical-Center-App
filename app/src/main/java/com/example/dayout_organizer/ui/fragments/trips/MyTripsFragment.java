package com.example.dayout_organizer.ui.fragments.trips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.MyTripsAdapter;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class MyTripsFragment extends Fragment {

    View view;

    @BindView(R.id.my_trips_search_field)
    SearchView myTripsSearchField;

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



        initRecycler();

        fakeFilteredList = filterList(fakeTrips, 3);
        for (TripModel model:
                fakeFilteredList) {
            System.out.println(model);
        }
        adapter.refreshList(fakeFilteredList, 3);

        myTripsTabLayout.addOnTabSelectedListener(tabListener);
        myTripsActionButton.setOnClickListener(onCreateTripClicked);


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
}