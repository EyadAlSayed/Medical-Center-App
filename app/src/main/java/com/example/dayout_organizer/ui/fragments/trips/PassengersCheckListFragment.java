package com.example.dayout_organizer.ui.fragments.trips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.CheckPassengersListAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.PassengerData;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

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

    LoadingDialog loadingDialog;

    int checked = 0;
    int unchecked = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_check_passengers_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromAPI();

        return view;
    }

    private void initViews() {
        loadingDialog = new LoadingDialog(requireContext());
        initRecycler();
        checkPassengersBackArrow.setOnClickListener(onBackClicked);
    }

    private void initRecycler() {
        passengersCheckListRecycler.setHasFixedSize(true);
        passengersCheckListRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CheckPassengersListAdapter(new ArrayList<>(), requireContext(), this);
        passengersCheckListRecycler.setAdapter(adapter);
    }

    private void getDataFromAPI(){
        loadingDialog.show();
        TripViewModel.getINSTANCE().getPassengersInTrip();
        TripViewModel.getINSTANCE().passengersInTripMutableLiveData.observe(requireActivity(), passengersObserver);
    }

    private final Observer<Pair<PassengerData, String>> passengersObserver = new Observer<Pair<PassengerData, String>>() {
        @Override
        public void onChanged(Pair<PassengerData, String> passengerDataStringPair) {
            loadingDialog.dismiss();
            if(passengerDataStringPair != null){
                if (passengerDataStringPair.first != null){
                    //setStatistics(passengerDataStringPair.first.data);
                    //adapter.refreshList(passengerDataStringPair.first.data);
                }
            }
        }
    };

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

    private void setStatistics(ArrayList<PassengerData> list){
        setCheckedPassengers(list);

        passengersCheckListTotal.setText(String.valueOf(list.size()));
        passengersCheckListChecked.setText(String.valueOf(checked));
        passengersCheckListUnchecked.setText(String.valueOf(unchecked));
    }

    private void setCheckedPassengers(List<PassengerData> list) {
        for (PassengerData passenger : list) {
            if (passenger.checked)
                checked++;
            else
                unchecked++;
        }
    }

    private final View.OnClickListener onBackClicked = v -> {FN.popStack(requireActivity());};
}