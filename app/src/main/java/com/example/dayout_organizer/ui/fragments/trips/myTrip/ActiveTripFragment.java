package com.example.dayout_organizer.ui.fragments.trips.myTrip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.MyTripsAdapter;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class ActiveTripFragment extends Fragment {

    View view;

    MyTripsAdapter adapter;

    @BindView(R.id.active_trip_rc)
    RecyclerView activeTripRc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_active_trips, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        initRc();
        getDataFromApi();
    }

    private void initRc() {
        activeTripRc.setHasFixedSize(true);
        activeTripRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MyTripsAdapter(new ArrayList<>(), requireContext());
        activeTripRc.setAdapter(adapter);
    }

    private void getDataFromApi(){
        TripViewModel.getINSTANCE().getActiveTrips();
        TripViewModel.getINSTANCE().activeTripsMutableLiveData.observe(requireActivity(), activeTripsObserver);
    }

    private final Observer<Pair<TripModel, String>> activeTripsObserver = new Observer<Pair<TripModel, String>>() {
        @Override
        public void onChanged(Pair<TripModel, String> tripModelStringPair) {
            if(tripModelStringPair != null){
                if(tripModelStringPair.first != null){
                    adapter.refreshList(tripModelStringPair.first.data, 3);
                } else
                    new ErrorDialog(requireContext(), tripModelStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection");
        }
    };
}
