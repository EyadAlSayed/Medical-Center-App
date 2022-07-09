package com.example.dayout_organizer.ui.fragments.trips.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.dayout_organizer.models.passenger.PassengerBookedFor;
import com.example.dayout_organizer.models.passenger.PassengerData;
import com.example.dayout_organizer.models.passenger.PassengerModel;
import com.example.dayout_organizer.models.trip.CustomerTripData;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.room.passengersRoom.database.PassengersDataBase;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.ui.dialogs.notify.WarningDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    List<PassengerData> list = new ArrayList<>();

    LoadingDialog loadingDialog;

    private boolean isUpcoming;

    private boolean isOld;

    private int tripId;

    TripData tripData;

    public PassengersListFragment(int tripId, boolean isUpcoming) {
        this.tripId = tripId;
        this.isUpcoming = isUpcoming;
    }

    public PassengersListFragment(int tripId, boolean isOld, TripData tripData) {
        this.tripId = tripId;
        this.isOld = isOld;
        this.tripData = tripData;
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
        if (isUpcoming) {
            adapter = new PassengersListAdapter(new ArrayList<>(), requireContext(), isUpcoming, tripId);
            new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(passengersListRecyclerView);
        } else if (isOld) {
            adapter = new PassengersListAdapter(new ArrayList<>(), requireContext(), isOld);
        } else {
            adapter = new PassengersListAdapter(new ArrayList<>(), requireContext(), isUpcoming, tripId);
        }
        passengersListRecyclerView.setAdapter(adapter);
    }

    private void getDataFromAPI() {
        if (isUpcoming) {
            loadingDialog.show();
            TripViewModel.getINSTANCE().getPassengersInTrip(tripId);
            TripViewModel.getINSTANCE().bookingPassengersInTripMutableLiveData.observe(requireActivity(), passengersObserver);
        } else if (isOld) {
            List<PassengerData> oldTripPassengers = getAllPassengers();
            adapter.refresh(oldTripPassengers);
            passengersListTotal.setText(String.valueOf(getTotalPassengers(oldTripPassengers)));
        } else {
            loadingDialog.show();
            TripViewModel.getINSTANCE().getAllPassengersInTrip(tripId);
            TripViewModel.getINSTANCE().allPassengersInTripMutableLiveData.observe(requireActivity(), allPassengersObserver);
        }
    }

    private List<PassengerData> getAllPassengers() {
        List<PassengerData> passengers = new ArrayList<>();
        for (CustomerTripData customer : tripData.customer_trips) {
            for (PassengerBookedFor passenger : customer.passengers) {
                passengers.add(new PassengerData(customer.user, customer.customer_id, passenger.passenger_name));
            }
        }
        return passengers;
    }

    private int getTotalPassengers(List<PassengerData> list) {
        if (isUpcoming) {
            int total = 0;
            for (PassengerData passenger : list) {
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
            if (passengerDataStringPair != null) {
                if (passengerDataStringPair.first != null) {
                    list = passengerDataStringPair.first.data;
                    adapter.refresh(passengerDataStringPair.first.data);
                    passengersListTotal.setText(String.valueOf(getTotalPassengers(passengerDataStringPair.first.data)));
                } else {
                    getDataFromRoom();
                    new ErrorDialog(requireContext(), passengerDataStringPair.second).show();
                }

            } else {
                getDataFromRoom();
                new ErrorDialog(requireContext(), getResources().getString(R.string.error_connection)).show();
            }
        }
    };

    private final Observer<Pair<PassengerModel, String>> allPassengersObserver = new Observer<Pair<PassengerModel, String>>() {
        @Override
        public void onChanged(Pair<PassengerModel, String> passengerModelStringPair) {
            loadingDialog.dismiss();
            if (passengerModelStringPair != null) {
                if (passengerModelStringPair.first != null) {
                    adapter.refresh(passengerModelStringPair.first.data);
                    passengersListTotal.setText(String.valueOf(getTotalPassengers(passengerModelStringPair.first.data)));
                } else {
                    getDataFromRoom();
                    new ErrorDialog(requireContext(), passengerModelStringPair.second).show();
                }

            } else {
                getDataFromRoom();
                new ErrorDialog(requireContext(), getResources().getString(R.string.error_connection)).show();
            }

        }
    };

    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            new WarningDialog(requireContext(), getResources().getString(R.string.canceling_booking), adapter, list, viewHolder.getAdapterPosition(), passengersListTotal).show();
        }
    };

    private final View.OnClickListener onBackClicked = v -> FN.popStack(requireActivity());




    private void getDataFromRoom() {
        PassengersDataBase.getINSTANCE(requireContext())
                .iPassengers()
                .getPassengers()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<PassengerData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<PassengerData> passengerData) {
                        if (isUpcoming) list = passengerData;
                        adapter.refresh(passengerData);
                        passengersListTotal.setText(String.valueOf(getTotalPassengers(passengerData)));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
