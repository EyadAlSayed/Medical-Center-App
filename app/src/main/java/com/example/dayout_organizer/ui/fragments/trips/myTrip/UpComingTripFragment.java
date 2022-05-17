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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class UpComingTripFragment extends Fragment {


    MyTripsAdapter adapter;
    View view;

    @BindView(R.id.up_coming_trip_rc)
    RecyclerView upComingTripRc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_up_coming_trip, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        initRc();
        getDataFromApi();
    }

    private void initRc(){
        upComingTripRc.setHasFixedSize(true);
        upComingTripRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MyTripsAdapter(new ArrayList<>(),requireContext());
        upComingTripRc.setAdapter(adapter);
    }

    private void getDataFromApi(){
        TripViewModel.getINSTANCE().getUpcomingTrips();
        TripViewModel.getINSTANCE().upcomingTripsMutableLiveData.observe(requireActivity(), upcomingTripObserver);
    }

    private final Observer<Pair<TripModel, String>> upcomingTripObserver = new Observer<Pair<TripModel, String>>() {
        @Override
        public void onChanged(Pair<TripModel, String> listStringPair) {
            if(listStringPair != null){
                if(listStringPair.first != null){
                    adapter.refreshList(listStringPair.first.data,2);
                } else{
                    new ErrorDialog(requireContext(), listStringPair.second).show();
                }
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

}