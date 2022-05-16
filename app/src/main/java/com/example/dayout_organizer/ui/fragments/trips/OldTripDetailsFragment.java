package com.example.dayout_organizer.ui.fragments.trips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;

import butterknife.ButterKnife;

public class OldTripDetailsFragment extends Fragment {

    View view;

    public OldTripDetailsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_old_trip_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
