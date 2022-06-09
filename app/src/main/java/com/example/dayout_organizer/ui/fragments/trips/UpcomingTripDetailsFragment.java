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

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.models.trip.TripType;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.ui.dialogs.WarningDialog;
import com.example.dayout_organizer.ui.fragments.trips.EditTrip.EditTripFragment;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

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

    @BindView(R.id.upcoming_trip_details_expire_date)
    TextView upcomingTripDetailsExpireDate;

    @BindView(R.id.upcoming_trips_end_booking_date)
    TextView upcomingTripsEndBookingDate;

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

    LoadingDialog loadingDialog;

    TripData data;

    public UpcomingTripDetailsFragment(TripData data) {
        this.data = data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upcoming_trip_details, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromApi();
        return view;
    }

    private void initViews(){
        loadingDialog = new LoadingDialog(requireContext());
        upcomingTripDetailsBackArrow.setOnClickListener(onBackClicked);
        upcomingTripDetailsEditIcon.setOnClickListener(onEditClicked);
        upcomingTripDetailsDeleteIcon.setOnClickListener(onDeleteClicked);
        upcomingTripDetailsPassengers.setOnClickListener(onPassengersClicked);
        upcomingTripDetailsPassengersFrontArrow.setOnClickListener(onPassengersClicked);
        upcomingTripDetailsRoadMap.setOnClickListener(onRoadMapClicked);
        upcomingTripDetailsRoadMapFrontArrow.setOnClickListener(onRoadMapClicked);
        upcomingTripDetailsCheckPassengers.setOnClickListener(onCheckClicked);
        upcomingTripDetailsBeginTrip.setOnClickListener(onBeginTripClicked);

    }

    private void hideAndShowIcons(){
        upcomingTripDetailsDeleteIcon.setVisibility(View.GONE);
        upcomingTripDetailsEditIcon.setVisibility(View.GONE);
        upcomingTripDetailsCheckPassengers.setVisibility(View.VISIBLE);
        upcomingTripDetailsBeginTrip.setVisibility(View.VISIBLE);
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

    private void setData(TripDetailsModel model){
        upcomingTripDetailsType.setText(getTypes(data.types));
        upcomingTripDetailsTitle.setText(data.title);
        upcomingTripDetailsDate.setText(data.begin_date);
        upcomingTripDetailsStops.setText(data.stopsToDetails);
        upcomingTripDetailsExpireDate.setText(data.expire_date);
        upcomingTripDetailsPrice.setText(String.valueOf(data.price));
        upcomingTripsEndBookingDate.setText(model.data.end_booking);
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
                    data = tripDetailsModelStringPair.first.data;
                    if (data.isActive) hideAndShowIcons();
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

    private final View.OnClickListener onEditClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.addFixedNameFadeFragment(MAIN_FRC,requireActivity(),new EditTripFragment(data));
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
