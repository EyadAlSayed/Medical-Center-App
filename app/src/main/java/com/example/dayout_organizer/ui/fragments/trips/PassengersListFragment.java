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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.PassengersListAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.passenger.PassengerData;
import com.example.dayout_organizer.models.passenger.PassengerModel;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.ui.dialogs.WarningDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class PassengersListFragment extends Fragment {

    View view;

    @BindView(R.id.passengers_list_back_button)
    ImageButton passengersListBackButton;

    @BindView(R.id.passengers_list_recycler_view)
    RecyclerView passengersListRecyclerView;

    @BindView(R.id.passengers_list_total)
    TextView passengersListTotal;

    PassengersListAdapter adapter;

    ArrayList<PassengerData> list = new ArrayList<>();

    LoadingDialog loadingDialog;

    private boolean isUpcoming;

    private int tripId;

    public PassengersListFragment(int tripId, boolean isUpcoming) {
        this.tripId = tripId;
        this.isUpcoming = isUpcoming;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_passengers_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromAPI();

        return view;
    }

    private void initViews() {
        initRecycler();
        loadingDialog = new LoadingDialog(requireContext());
        passengersListBackButton.setOnClickListener(onBackClicked);
    }

    private void initRecycler() {
        passengersListRecyclerView.setHasFixedSize(true);
        passengersListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PassengersListAdapter(new ArrayList<>(), requireContext(), isUpcoming, tripId);
        if (isUpcoming)
            new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(passengersListRecyclerView);
        passengersListRecyclerView.setAdapter(adapter);
    }

    private void getDataFromAPI(){
        loadingDialog.show();
        if(isUpcoming) {
            TripViewModel.getINSTANCE().getPassengersInTrip(tripId);
            TripViewModel.getINSTANCE().bookingPassengersInTripMutableLiveData.observe(requireActivity(), passengersObserver);
        } else {
            TripViewModel.getINSTANCE().getAllPassengersInTrip(tripId);
            TripViewModel.getINSTANCE().allPassengersInTripMutableLiveData.observe(requireActivity(), allPassengersObserver);
        }
    }

    private int getTotalPassengers(List<PassengerData> list){
        if(isUpcoming){
            int total = 0;
            for (PassengerData passenger : list){
                total += passenger.passengers.size();
            }
            return total;
        } else
            return list.size();
    }

    private final Observer<Pair<PassengerModel, String>> passengersObserver = new Observer<Pair<PassengerModel, String>>() {
        @Override
        public void onChanged(Pair<PassengerModel, String> passengerDataStringPair) {
            loadingDialog.dismiss();
            if(passengerDataStringPair != null){
                if(passengerDataStringPair.first != null){
                    list = passengerDataStringPair.first.data;
                    adapter.refreshList(passengerDataStringPair.first.data);
                    passengersListTotal.setText(String.valueOf(getTotalPassengers(passengerDataStringPair.first.data)));
                } else
                    new ErrorDialog(requireContext(), passengerDataStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

    private final Observer<Pair<PassengerModel, String>> allPassengersObserver = new Observer<Pair<PassengerModel, String>>() {
        @Override
        public void onChanged(Pair<PassengerModel, String> passengerModelStringPair) {
            loadingDialog.dismiss();
            if(passengerModelStringPair != null){
                if (passengerModelStringPair.first != null){
                    adapter.refreshList(passengerModelStringPair.first.data);
                    passengersListTotal.setText(String.valueOf(getTotalPassengers(passengerModelStringPair.first.data)));
                } else
                    new ErrorDialog(requireContext(), passengerModelStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            list.remove(viewHolder.getAdapterPosition());
//            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        //    new WarningDialog(requireContext(), "Are you sure you want to cancel this passenger's booking?", adapter, list, viewHolder.getAdapterPosition()).show();
        }
    };

    private final View.OnClickListener onBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.popStack(requireActivity());
        }
    };

    private final View.OnClickListener onSubmitClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
