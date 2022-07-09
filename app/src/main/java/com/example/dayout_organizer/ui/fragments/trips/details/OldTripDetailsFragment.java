package com.example.dayout_organizer.ui.fragments.trips.details;

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

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.tripType.TripType;
import com.example.dayout_organizer.room.tripRoom.databases.TripDataBases;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.ui.fragments.trips.details.PassengersListFragment;
import com.example.dayout_organizer.ui.fragments.trips.details.RoadMapFragment;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

@SuppressLint("NonConstantResourceId")
public class OldTripDetailsFragment extends Fragment {

    View view;

    @BindView(R.id.old_trip_details_back_arrow)
    ImageButton oldTripDetailsBackArrow;

    @BindView(R.id.old_trip_details_title)
    TextView oldTripDetailsTitle;

    @BindView(R.id.old_trip_details_type)
    TextView oldTripDetailsType;

    @BindView(R.id.old_trip_details_stops)
    TextView oldTripDetailsStops;

    @BindView(R.id.old_trip_details_date)
    TextView oldTripDetailsDate;

    @BindView(R.id.old_trip_details_price)
    TextView oldTripDetailsPrice;

    @BindView(R.id.old_trip_details_expire_date)
    TextView oldTripDetailsExpireDate;

    @BindView(R.id.old_trips_end_booking_date)
    TextView oldTripsEndBookingDate;

    @BindView(R.id.old_trip_details_roadmap)
    TextView oldTripDetailsRoadMap;

    @BindView(R.id.old_trip_details_roadmap_front_arrow)
    ImageButton oldTripDetailsRoadMapFrontArrow;

    @BindView(R.id.old_trip_details_passengers)
    TextView oldTripDetailsPassengers;

    @BindView(R.id.old_trip_details_passengers_front_arrow)
    ImageButton oldTripDetailsPassengersFrontArrow;

    LoadingDialog loadingDialog;

    int tripId;
    TripData data;

    public OldTripDetailsFragment(int tripId) {
        this.tripId = tripId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_old_trip_details, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromApi();
        return view;
    }

    @Override
    public void onStart(){
        ((MainActivity) requireActivity()).hideBottomBar();
        super.onStart();
    }

    private void initViews(){
        loadingDialog = new LoadingDialog(requireContext());
        oldTripDetailsBackArrow.setOnClickListener(onBackClicked);
        oldTripDetailsRoadMap.setOnClickListener(onRoadMapClicked);
        oldTripDetailsRoadMapFrontArrow.setOnClickListener(onRoadMapClicked);
        oldTripDetailsPassengers.setOnClickListener(onPassengersClicked);
        oldTripDetailsPassengersFrontArrow.setOnClickListener(onPassengersClicked);
    }

    private String getTypes(List<TripType> types){
        String tripTypes = "";

        for(int i = 0; i < types.size(); i++){
            if (i != 0) {
                tripTypes += ", " + types.get(i).name;
            } else if(i == 0)
                tripTypes += types.get(i).name;
        }

        return tripTypes;
    }

    private void setData(TripData data){
        oldTripDetailsType.setText(getTypes(data.types));
        oldTripDetailsTitle.setText(data.title);
        oldTripDetailsStops.setText(data.stopsToDetails);
        oldTripDetailsDate.setText(data.begin_date);
        oldTripDetailsExpireDate.setText(data.expire_date);
        oldTripDetailsPrice.setText(String.valueOf(data.price));
        oldTripsEndBookingDate.setText(data.end_booking);
    }

    private void getDataFromApi(){
        loadingDialog.show();
        TripViewModel.getINSTANCE().getTripDetails(tripId);
        TripViewModel.getINSTANCE().tripDetailsMutableLiveData.observe(requireActivity(), tripDetailsObserver);
    }

    private final Observer<Pair<TripDetailsModel, String>> tripDetailsObserver = new Observer<Pair<TripDetailsModel, String>>() {
        @Override
        public void onChanged(Pair<TripDetailsModel, String> tripDetailsModelStringPair) {
            loadingDialog.dismiss();
            if(tripDetailsModelStringPair != null){
                if(tripDetailsModelStringPair.first != null){
                    data = tripDetailsModelStringPair.first.data;
                    setData(tripDetailsModelStringPair.first.data);
                    ((MainActivity)requireActivity()).iTrip.insertTripData(tripDetailsModelStringPair.first.data);
                } else{
                    getDataFromRoom();
                    new ErrorDialog(requireContext(), tripDetailsModelStringPair.second).show();
                }

            } else{
                getDataFromRoom();
                new ErrorDialog(requireContext(), getResources().getString(R.string.error_connection)).show();
            }

        }
    };

    private final View.OnClickListener onBackClicked = v -> FN.popStack(requireActivity());

    private final View.OnClickListener onRoadMapClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.addFixedNameFadeFragment(MAIN_FRC,requireActivity(),new RoadMapFragment(tripId));
        }
    };

    private final View.OnClickListener onPassengersClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new PassengersListFragment(data.id, true, data));
        }
    };

    private void getDataFromRoom() {
        TripDataBases.getINSTANCE(requireContext())
                .iTrip()
                .getTripDataById(tripId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<TripData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull TripData tripData) {
                        data = tripData;
                        setData(tripData);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
