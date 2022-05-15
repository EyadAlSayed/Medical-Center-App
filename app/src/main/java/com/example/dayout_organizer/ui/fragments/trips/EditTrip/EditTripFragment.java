package com.example.dayout_organizer.ui.fragments.trips.EditTrip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;

import butterknife.ButterKnife;


public class EditTripFragment extends Fragment {





    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
}