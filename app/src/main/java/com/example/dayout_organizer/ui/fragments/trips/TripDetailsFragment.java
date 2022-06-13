package com.example.dayout_organizer.ui.fragments.trips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.tripType.TripType;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.ui.fragments.trips.EditTrip.EditTripFragment;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

@SuppressLint("NonConstantResourceId")
public class TripDetailsFragment extends Fragment {

    View view;

    @BindView(R.id.trip_details_back_arrow)
    ImageButton tripDetailsBackArrow;

    @BindView(R.id.trip_details_edit_icon)
    ImageButton tripDetailsEditIcon;

    @BindView(R.id.trip_details_delete_icon)
    ImageButton tripDetailsDeleteIcon;

    @BindView(R.id.trip_details_title)
    TextView tripDetailsTitle;

    @BindView(R.id.trip_details_type)
    TextView tripDetailsType;

    @BindView(R.id.trip_details_stops)
    TextView tripDetailsStops;

    @BindView(R.id.trip_details_date)
    TextView tripDetailsDate;

    @BindView(R.id.trip_details_price)
    TextView tripDetailsPrice;

    @BindView(R.id.trip_details_expire_date)
    TextView tripDetailsExpireDate;

    @BindView(R.id.trips_end_booking_date)
    TextView tripsEndBookingDate;

    @BindView(R.id.trip_details_roadmap)
    TextView tripDetailsRoadMap;

    @BindView(R.id.trip_details_roadmap_front_arrow)
    ImageButton tripDetailsRoadMapFrontArrow;

    @BindView(R.id.trip_details_passengers)
    TextView tripDetailsPassengers;

    @BindView(R.id.trip_details_passengers_front_arrow)
    ImageButton tripDetailsPassengersFrontArrow;

    @BindView(R.id.trip_details_check_passengers)
    Button tripDetailsCheckPassengers;

    @BindView(R.id.trip_change_status)
    Button changeStatButton;

    @BindView(R.id.trip_details_go_to_passengers)
    LinearLayout tripDetailsGoToPassengers;

    LoadingDialog loadingDialog;

    TripData data;


    public TripDetailsFragment(TripData data) {
        this.data = data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_details, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromApi();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity) requireActivity()).hideBottomBar();
        super.onStart();
    }

    private void initViews() {
        loadingDialog = new LoadingDialog(requireContext());
        tripDetailsBackArrow.setOnClickListener(onBackClicked);
        tripDetailsEditIcon.setOnClickListener(onEditClicked);
        tripDetailsDeleteIcon.setOnClickListener(onDeleteClicked);
        tripDetailsPassengers.setOnClickListener(onPassengersClicked);
        tripDetailsPassengersFrontArrow.setOnClickListener(onPassengersClicked);
        tripDetailsRoadMap.setOnClickListener(onRoadMapClicked);
        tripDetailsRoadMapFrontArrow.setOnClickListener(onRoadMapClicked);
        tripDetailsCheckPassengers.setOnClickListener(onCheckClicked);
        changeStatButton.setOnClickListener(onChangeStateClicked);
        tripDetailsGoToPassengers.setOnClickListener(onPassengersClicked);

    }

    private void hideAndShowIcons() {
        tripDetailsDeleteIcon.setVisibility(View.GONE);
        tripDetailsEditIcon.setVisibility(View.GONE);
        tripDetailsCheckPassengers.setVisibility(View.VISIBLE);
        changeStatButton.setVisibility(View.VISIBLE);

        if (data.trip_status_id == 1) changeStatButton.setText("End Trip");
        else changeStatButton.setText("Begin Trip");
    }

    private String getTypes(List<TripType> types) {
        String tripTypes = "";

        for (int i = 0; i < types.size(); i++) {
            if (i != 0) {
                tripTypes += ", " + types.get(i).name;
            } else if (i == 0)
                tripTypes += types.get(i).name;
        }

        return tripTypes;
    }

    private void setData(TripDetailsModel model) {
        tripDetailsType.setText(getTypes(data.types));
        tripDetailsTitle.setText(data.title);
        tripDetailsDate.setText(data.begin_date);
        tripDetailsStops.setText(data.stopsToDetails);
        tripDetailsExpireDate.setText(data.expire_date);
        tripDetailsPrice.setText(String.valueOf(data.price));
        tripsEndBookingDate.setText(model.data.end_booking);
    }

    private void getDataFromApi() {
        loadingDialog.show();
        TripViewModel.getINSTANCE().getTripDetails(data.id);
        TripViewModel.getINSTANCE().tripDetailsMutableLiveData.observe(requireActivity(), tripDetailsObserver);
    }

    private final Observer<Pair<TripDetailsModel, String>> tripDetailsObserver = new Observer<Pair<TripDetailsModel, String>>() {
        @Override
        public void onChanged(Pair<TripDetailsModel, String> tripDetailsModelStringPair) {
            loadingDialog.dismiss();
            if (tripDetailsModelStringPair != null) {
                if (tripDetailsModelStringPair.first != null) {
                    setData(tripDetailsModelStringPair.first);
                    if (data.isActive) hideAndShowIcons();
                    else data.isActive = true;
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
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new EditTripFragment(data));
        }
    };

    private final View.OnClickListener onDeleteClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        //    new WarningDialog(requireContext(), getResources().getString(R.string.deleting_trip)).show();
        }
    };

    private final View.OnClickListener onPassengersClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean is = false;
            if (data.isUpcoming)
                is = true;
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new PassengersListFragment(data.id, is));
        }
    };

    private final View.OnClickListener onRoadMapClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.addFixedNameFadeFragment(MAIN_FRC,requireActivity(),new RoadMapFragment(data.id));
        }
    };

    private final View.OnClickListener onCheckClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new PassengersCheckListFragment(data.id));
        }
    };

    private final View.OnClickListener onChangeStateClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /**
             * Trip Status id value
             * 1 - started
             * 2 - ended
             * 3 - in progress
             * 4 - full
             */
            if (data.trip_status_id == 1) {
                sendEndTripRequest();
            } else {
                sendBeginTripRequest();
            }
        }
    };

    private void sendBeginTripRequest() {
        TripViewModel.getINSTANCE().beginTrip(data.id);
        TripViewModel.getINSTANCE().successfulMutableLiveData.observe(requireActivity(),changeStatusObserver);
    }

    private void sendEndTripRequest() {
        TripViewModel.getINSTANCE().endTrip(data.id);
        TripViewModel.getINSTANCE().successfulMutableLiveData.observe(requireActivity(),changeStatusObserver);
    }
    private final Observer<Pair<Boolean,String>> changeStatusObserver = new Observer<Pair<Boolean, String>>() {
        @Override
        public void onChanged(Pair<Boolean, String> booleanStringPair) {
            if (booleanStringPair != null){
                if (booleanStringPair.first != null){
                    if (data.trip_status_id == 1) NoteMessage.showSnackBar(requireActivity(),"Trip end Successfully");
                    else NoteMessage.showSnackBar(requireActivity(),"Trip begin Successfully");
                    FN.popTopStack(requireActivity());
                }
                else {
                    new ErrorDialog(requireContext(),booleanStringPair.second).show();
                }
            }else {
                new ErrorDialog(requireContext(),"Error Connection").show();
            }
        }
    };
}
