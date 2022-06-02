package com.example.dayout_organizer.ui.fragments.trips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.CheckPassengersListAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.PassengerData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class PassengersCheckListFragment extends Fragment {

    View view;

    @BindView(R.id.check_passengers_back_arrow)
    ImageButton checkPassengersBackArrow;

    @BindView(R.id.passengers_check_list_total)
    TextView passengersCheckListTotal;

    @BindView(R.id.passengers_check_list_checked)
    TextView passengersCheckListChecked;

    @BindView(R.id.passengers_check_list_unchecked)
    TextView passengersCheckListUnchecked;

    @BindView(R.id.passengers_check_list_recycler)
    RecyclerView passengersCheckListRecycler;

    CheckPassengersListAdapter adapter;

    int checked = 0;
    int unchecked = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_check_passengers_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        //getDataFromAPI();

        ArrayList<PassengerData> list = new ArrayList<>();

        PassengerData p1 = new PassengerData("SheeZ Farah", false);
        PassengerData p2 = new PassengerData("Ab Ayham", true);
        PassengerData p3 = new PassengerData("Sheikh Scientist", true);
        PassengerData p4 = new PassengerData("Abd Cute", false);
        PassengerData p11 = new PassengerData("SheeZ Farah", false);
        PassengerData p22 = new PassengerData("Ab Ayham", true);
        PassengerData p33 = new PassengerData("Sheikh Scientist", true);
        PassengerData p44 = new PassengerData("Abd Cute", false);
        PassengerData p111 = new PassengerData("SheeZ Farah", false);
        PassengerData p222 = new PassengerData("Ab Ayham", true);
        PassengerData p333 = new PassengerData("Sheikh Scientist", true);
        PassengerData p444 = new PassengerData("Abd Cute", false);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p11);
        list.add(p22);
        list.add(p33);
        list.add(p44);
        list.add(p111);
        list.add(p222);
        list.add(p333);
        list.add(p444);

        setCheckedPassengers(list);

        passengersCheckListTotal.setText(String.valueOf(list.size()));
        passengersCheckListChecked.setText(String.valueOf(checked));
        passengersCheckListUnchecked.setText(String.valueOf(unchecked));

        adapter.refreshList(list);

        return view;
    }

    private void initViews() {
        initRecycler();
        checkPassengersBackArrow.setOnClickListener(onBackClicked);
    }

    private void initRecycler() {
        passengersCheckListRecycler.setHasFixedSize(true);
        passengersCheckListRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CheckPassengersListAdapter(new ArrayList<>(), requireContext(), this);
        passengersCheckListRecycler.setAdapter(adapter);
    }

    public void passengerCheckBoxChanged(boolean checked) {
        if(checked) {
            this.checked++;
            this.unchecked--;
        } else {
            this.checked--;
            this.unchecked++;
        }
        passengersCheckListChecked.setText(String.valueOf(this.checked));
        passengersCheckListUnchecked.setText(String.valueOf(this.unchecked));
    }

    private void setCheckedPassengers(List<PassengerData> list) {
        for (PassengerData passenger : list) {
            if (passenger.checked)
                checked++;
            else
                unchecked++;
        }
    }

    private final View.OnClickListener onBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.popStack(requireActivity());
        }
    };
}