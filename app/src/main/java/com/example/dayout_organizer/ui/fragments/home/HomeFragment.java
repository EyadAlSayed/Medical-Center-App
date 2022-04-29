package com.example.dayout_organizer.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.HomePlaceAdapter;
import com.example.dayout_organizer.ui.activities.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {


    View view;
    @BindView(R.id.home_place_rc)
    RecyclerView homePlaceRc;

    HomePlaceAdapter homePlaceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    private void initView() {
        initRc();
    }

    private void initRc(){
        homePlaceRc.setHasFixedSize(true);
        homePlaceRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        homePlaceAdapter = new HomePlaceAdapter(new ArrayList<>(),requireContext());
        homePlaceRc.setAdapter(homePlaceAdapter);
    }
}