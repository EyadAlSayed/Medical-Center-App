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

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    TripModel.Data data;

    public OldTripDetailsFragment(TripModel.Data data) {
        this.data = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_old_trip_details, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromApi();
        return view;
    }

    private void initViews(){
        loadingDialog = new LoadingDialog(requireContext());
        oldTripDetailsBackArrow.setOnClickListener(onBackClicked);
        oldTripDetailsRoadMap.setOnClickListener(onRoadMapClicked);
        oldTripDetailsRoadMapFrontArrow.setOnClickListener(onRoadMapClicked);
        oldTripDetailsPassengers.setOnClickListener(onPassengersClicked);
        oldTripDetailsPassengersFrontArrow.setOnClickListener(onPassengersClicked);
    }

    private String getTypes(ArrayList<TripDetailsModel.Type> types){
        String tripTypes = "";

        for(int i = 0; i < types.size(); i++){
            if (i != 0) {
                tripTypes += ", " + types.get(i).name;
            } else if(i == 0)
                tripTypes += types.get(i).name;
        }

        return tripTypes;
    }

    private void setData(TripDetailsModel model){
        oldTripDetailsType.setText(getTypes(model.data.types));
        oldTripDetailsTitle.setText(data.title);
        oldTripDetailsStops.setText(data.stopsToDetails);
        oldTripDetailsDate.setText(data.begin_date);
        oldTripDetailsExpireDate.setText(model.data.end_booking);
        oldTripDetailsPrice.setText(String.valueOf(data.price));
        oldTripsEndBookingDate.setText(data.expire_date);
    }

    private void getDataFromApi(){
        loadingDialog.show();
        TripViewModel.getINSTANCE().getTripDetails(data.id);
        TripViewModel.getINSTANCE().tripDetailsMutableLiveData.observe(requireActivity(), tripDetailsObserver);
    }

    private final Observer<Pair<TripDetailsModel, String>> tripDetailsObserver = new Observer<Pair<TripDetailsModel, String>>() {
        @Override
        public void onChanged(Pair<TripDetailsModel, String> tripDetailsModelStringPair) {
            loadingDialog.dismiss();
            if(tripDetailsModelStringPair != null){
                if(tripDetailsModelStringPair.first != null){
                    setData(tripDetailsModelStringPair.first);
                } else
                    new ErrorDialog(requireContext(), tripDetailsModelStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

    private final View.OnClickListener onBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.popStack(requireActivity());
        }
    };

    private final View.OnClickListener onRoadMapClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private final View.OnClickListener onPassengersClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
