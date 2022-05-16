package com.example.dayout_organizer.ui.fragments.trips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.ui.dialogs.WarningDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class UpcomingTripDetailsFragment extends Fragment {

    View view;

    @BindView(R.id.upcoming_trip_details_back_arrow)
    ImageButton upcomingTripDetailsBackArrow;

    @BindView(R.id.upcoming_trip_details_edit_icon)
    ImageButton upcomingTripDetailsEditIcon;

    @BindView(R.id.upcoming_trip_details_delete_icon)
    ImageButton upcomingTripDetailsDeleteIcon;

    @BindView(R.id.upcoming_trip_details_title)
    TextView upcomingTripDetailsTitle;

    @BindView(R.id.upcoming_trip_details_type)
    TextView upcomingTripDetailsType;

    @BindView(R.id.upcoming_trip_details_stops)
    TextView upcomingTripDetailsStops;

    @BindView(R.id.upcoming_trip_details_date)
    TextView upcomingTripDetailsDate;

    @BindView(R.id.upcoming_trip_details_price)
    TextView upcomingTripDetailsPrice;

    @BindView(R.id.upcoming_trip_details_end_booking_date)
    TextView upcomingTripDetailsEndBookingDate;

    @BindView(R.id.upcoming_trips_end_confirmation_date)
    TextView upcomingTripsEndConfirmationDate;

    @BindView(R.id.upcoming_trip_details_roadmap)
    TextView upcomingTripDetailsRoadMap;
    @BindView(R.id.upcoming_trip_details_roadmap_front_arrow)
    ImageButton upcomingTripDetailsRoadMapFrontArrow;

    @BindView(R.id.upcoming_trip_details_passengers)
    TextView upcomingTripDetailsPassengers;

    @BindView(R.id.upcoming_trip_details_passengers_front_arrow)
    ImageButton upcomingTripDetailsPassengersFrontArrow;

    @BindView(R.id.upcoming_trip_details_check_passengers)
    Button upcomingTripDetailsCheckPassengers;

    @BindView(R.id.upcoming_trip_details_begin_trip)
    Button upcomingTripDetailsBeginTrip;

    TripModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upcoming_trip_details, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    public UpcomingTripDetailsFragment(TripModel model){
        this.model = model;
    }

    public UpcomingTripDetailsFragment(){
    }


    private void initViews(){
        upcomingTripDetailsBackArrow.setOnClickListener(onBackClicked);
        upcomingTripDetailsEditIcon.setOnClickListener(onEditClicked);
        upcomingTripDetailsDeleteIcon.setOnClickListener(onDeleteClicked);
        upcomingTripDetailsPassengers.setOnClickListener(onPassengersClicked);
        upcomingTripDetailsPassengersFrontArrow.setOnClickListener(onPassengersClicked);
        upcomingTripDetailsRoadMap.setOnClickListener(onRoadMapClicked);
        upcomingTripDetailsRoadMapFrontArrow.setOnClickListener(onRoadMapClicked);
        upcomingTripDetailsCheckPassengers.setOnClickListener(onCheckClicked);
        upcomingTripDetailsBeginTrip.setOnClickListener(onBeginTripClicked);

        if(model.isActive){
            upcomingTripDetailsDeleteIcon.setVisibility(View.GONE);
            upcomingTripDetailsEditIcon.setVisibility(View.GONE);
            upcomingTripDetailsCheckPassengers.setVisibility(View.VISIBLE);
            upcomingTripDetailsBeginTrip.setVisibility(View.VISIBLE);
        }
    }

    private final View.OnClickListener onBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.popStack(requireActivity());
        }
    };

    private final View.OnClickListener onEditClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private final View.OnClickListener onDeleteClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new WarningDialog(requireContext(), getResources().getString(R.string.deleting_trip)).show();
        }
    };

    private final View.OnClickListener onPassengersClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private final View.OnClickListener onRoadMapClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private final View.OnClickListener onCheckClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private final View.OnClickListener onBeginTripClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
