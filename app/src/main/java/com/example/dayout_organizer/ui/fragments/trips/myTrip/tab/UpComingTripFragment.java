package com.example.dayout_organizer.ui.fragments.trips.myTrip.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dayout_organizer.R;

import com.example.dayout_organizer.adapter.recyclers.myTrips.UpComingTripAdapter;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class UpComingTripFragment extends Fragment {



    View view;

    @BindView(R.id.up_coming_trip_rc)
    RecyclerView upComingTripRc;

    @BindView(R.id.upcoming_trips_no_trips)
    TextView upcomingTripsNoTrips;

    @BindView(R.id.upcoming_trips_refresh_layout)
    SwipeRefreshLayout upcomingTripsRefreshLayout;

    LoadingDialog loadingDialog;
    UpComingTripAdapter adapter;
    public UpComingTripFragment(UpComingTripAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_up_coming_trip, container, false);
        ButterKnife.bind(this, view);
        initView();
        getDataFromApi();
        return view;
    }

    private void initView() {
        loadingDialog = new LoadingDialog(requireContext());
        initRc();
    }

    private void initRc() {
        upComingTripRc.setHasFixedSize(true);
        upComingTripRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        upComingTripRc.setAdapter(adapter);
    }

    private void setAsUpcoming(ArrayList<TripData> list) {
        for (TripData trip : list) {
            trip.isUpcoming = true;
        }
    }

    private void getDataFromApi() {
        loadingDialog.show();
        TripViewModel.getINSTANCE().getUpcomingTrips();
        TripViewModel.getINSTANCE().upcomingTripsMutableLiveData.observe(requireActivity(), upcomingTripObserver);
    }

    private final Observer<Pair<TripModel, String>> upcomingTripObserver = new Observer<Pair<TripModel, String>>() {
        @Override
        public void onChanged(Pair<TripModel, String> listStringPair) {
            loadingDialog.dismiss();
            if (listStringPair != null) {
                if (listStringPair.first != null) {
                    if (listStringPair.first.data.isEmpty()) {
                        upcomingTripsRefreshLayout.setVisibility(View.GONE);
                        upcomingTripsNoTrips.setVisibility(View.VISIBLE);
                    } else {
                        upcomingTripsRefreshLayout.setVisibility(View.VISIBLE);
                        upcomingTripsNoTrips.setVisibility(View.GONE);
                        setAsUpcoming(listStringPair.first.data);
                        adapter.refresh(listStringPair.first.data);
                    }
                }else {
                    new ErrorDialog(requireContext(), listStringPair.second).show();
                }
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

}