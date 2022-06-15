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

import com.example.dayout_organizer.adapter.recyclers.myTrips.ActiveTripAdapter;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class ActiveTripFragment extends Fragment {

    View view;

    @BindView(R.id.active_trip_rc)
    RecyclerView activeTripRc;

    @BindView(R.id.active_trips_no_active_trips)
    TextView activeTripsNoActiveTrips;

    //TODO: Activate the refresh thing - Caesar.
    @BindView(R.id.active_trips_refresh_layout)
    SwipeRefreshLayout activeTripsRefreshLayout;

    LoadingDialog loadingDialog;
    ActiveTripAdapter adapter;
    public ActiveTripFragment(ActiveTripAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_active_trips, container, false);
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
        activeTripRc.setHasFixedSize(true);
        activeTripRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        activeTripRc.setAdapter(adapter);
    }

    private void setAsActive(ArrayList<TripData> list) {
        for (TripData trip : list) {
            trip.isActive = true;
        }
    }

    private void getDataFromApi() {
        loadingDialog.show();
        TripViewModel.getINSTANCE().getActiveTrips();
        TripViewModel.getINSTANCE().activeTripsMutableLiveData.observe(requireActivity(), activeTripsObserver);
    }

    private final Observer<Pair<TripModel, String>> activeTripsObserver = new Observer<Pair<TripModel, String>>() {
        @Override
        public void onChanged(Pair<TripModel, String> tripModelStringPair) {
            loadingDialog.dismiss();
            if (tripModelStringPair != null) {
                if (tripModelStringPair.first != null) {
                    if (tripModelStringPair.first.data.isEmpty()) {
                        activeTripsRefreshLayout.setVisibility(View.GONE);
                        activeTripsNoActiveTrips.setVisibility(View.VISIBLE);
                    } else {
                        activeTripsRefreshLayout.setVisibility(View.VISIBLE);
                        activeTripsNoActiveTrips.setVisibility(View.GONE);
                        setAsActive(tripModelStringPair.first.data);
                        adapter.refresh(tripModelStringPair.first.data);
                    }
                }else
                    new ErrorDialog(requireContext(), tripModelStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection");
        }
    };
}
