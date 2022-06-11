package com.example.dayout_organizer.ui.fragments.trips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.CheckPassengersListAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.passenger.CheckPassengerModel;
import com.example.dayout_organizer.models.passenger.PassengerData;
import com.example.dayout_organizer.models.passenger.PassengerModel;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

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

    @BindView(R.id.check_passengers_submit_button)
    Button submitButton;

    CheckPassengersListAdapter adapter;

    LoadingDialog loadingDialog;

    ArrayList<Integer> tmpList;
    List<PassengerData> list;

    private int tripId;

    int checked = 0;
    int unchecked = 0;

    public PassengersCheckListFragment(int tripId){
        this.tripId = tripId;
    }

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
        submitButton.setOnClickListener(onSubmitClicked);
        tmpList = new ArrayList<>();
        list = new ArrayList<>();
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
        TripViewModel.getINSTANCE().getAllPassengersInTrip(tripId);
        TripViewModel.getINSTANCE().allPassengersInTripMutableLiveData.observe(requireActivity(), passengersObserver);
    }

    public void passengerCheckBoxChanged(int passengerPosition, boolean checked) {
        if(checked) {
            tmpList.add(list.get(passengerPosition).id);
            this.checked++;
            this.unchecked--;
        } else {
            System.out.println(list.get(passengerPosition).id);
            tmpList.remove((Object)list.get(passengerPosition).id);
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
            if (passenger.checkout == 1) {
                checked++;
                tmpList.add(passenger.id);
            }
            else
                unchecked++;
        }
    }

    private CheckPassengerModel getModel(){
        CheckPassengerModel model = new CheckPassengerModel();
        model.trip_id = tripId;
        model.passengers_ids = tmpList;
        return model;
    }

    private void submitChanges(){
        loadingDialog.show();
        TripViewModel.getINSTANCE().checkPassengers(getModel());
        TripViewModel.getINSTANCE().checkPassengersMutableLiveData.observe(requireActivity(), checkObserver);
    }

    private final Observer<Pair<PassengerModel, String>> passengersObserver = new Observer<Pair<PassengerModel, String>>() {
        @Override
        public void onChanged(Pair<PassengerModel, String> passengerDataStringPair) {
            loadingDialog.dismiss();
            if(passengerDataStringPair != null){
                if (passengerDataStringPair.first != null){
                    list = passengerDataStringPair.first.data;
                    setStatistics(passengerDataStringPair.first.data);
                    adapter.refreshList(passengerDataStringPair.first.data);
                }
            }
        }
    };

    private final Observer<Pair<ResponseBody, String>> checkObserver = new Observer<Pair<ResponseBody, String>>() {
        @Override
        public void onChanged(Pair<ResponseBody, String> responseBodyStringPair) {
            loadingDialog.dismiss();
            if(responseBodyStringPair != null){
                if(responseBodyStringPair.first != null){
                    NoteMessage.message(requireContext(), "Changes Submitted!");
                    tmpList.clear();
                } else
                    new ErrorDialog(requireContext(), responseBodyStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

    private final View.OnClickListener onBackClicked = v -> {FN.popStack(requireActivity());};

    private final View.OnClickListener onSubmitClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitChanges();
        }
    };
}