package com.example.dayout_organizer.ui.fragments.trips.details;

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
import com.example.dayout_organizer.room.tripRoom.databases.TripDataBases;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.ui.dialogs.notify.WarningDialog;
import com.example.dayout_organizer.ui.fragments.trips.editTrip.EditTripFragment;
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
    LinearLayout tripDetailsRoadMap;

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
    int tripId;

    public TripDetailsFragment(int tripId) {
        this.tripId = tripId;
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

    private void hideAndShowIcons(int tripStatusId) {
        tripDetailsDeleteIcon.setVisibility(View.GONE);
        tripDetailsEditIcon.setVisibility(View.GONE);
        tripDetailsCheckPassengers.setVisibility(View.VISIBLE);

        if (tripStatusId == 1) {
            changeStatButton.setVisibility(View.VISIBLE);
            changeStatButton.setText("End Trip");
        }
        else if(tripStatusId == 4){
            changeStatButton.setVisibility(View.VISIBLE);
            changeStatButton.setText("Begin Trip");
        }

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

    private void setData(TripData data) {
        tripDetailsType.setText(getTypes(data.types));
        tripDetailsTitle.setText(data.title);
        tripDetailsDate.setText(data.begin_date);
        tripDetailsStops.setText(data.stopsToDetails);
        tripDetailsExpireDate.setText(data.expire_date);
        tripDetailsPrice.setText(String.valueOf(data.price));
        tripsEndBookingDate.setText(data.end_booking);
    }

    private void getDataFromApi() {
        loadingDialog.show();
        TripViewModel.getINSTANCE().getTripDetails(tripId);
        TripViewModel.getINSTANCE().tripDetailsMutableLiveData.observe(requireActivity(), tripDetailsObserver);
    }

    private final Observer<Pair<TripDetailsModel, String>> tripDetailsObserver = new Observer<Pair<TripDetailsModel, String>>() {
        @Override
        public void onChanged(Pair<TripDetailsModel, String> tripDetailsModelStringPair) {
            loadingDialog.dismiss();
            if (tripDetailsModelStringPair != null) {
                if (tripDetailsModelStringPair.first != null) {
                    setData(tripDetailsModelStringPair.first.data);
                    ((MainActivity) requireActivity()).iTrip.insertTripData(tripDetailsModelStringPair.first.data);
                    if (data.isActive)
                        hideAndShowIcons(tripDetailsModelStringPair.first.data.trip_status_id);

                } else {
                    getDataFromRoom();
                    new ErrorDialog(requireContext(), tripDetailsModelStringPair.second).show();
                }
            }else {
                getDataFromRoom();
                new ErrorDialog(requireContext(), "Error Connection").show();
            }
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

    private final View.OnClickListener onDeleteClicked = v -> {
      //  new WarningDialog(requireContext(), getResources().getString(R.string.deleting_trip)).show();
    };

    private final View.OnClickListener onPassengersClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean is = false;
            if (data.isUpcoming)
                is = true;
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new PassengersListFragment(tripId, is));
        }
    };

    private final View.OnClickListener onRoadMapClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.addFixedNameFadeFragment(MAIN_FRC,requireActivity(),new RoadMapFragment(tripId));
        }
    };

    private final View.OnClickListener onCheckClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new PassengersCheckListFragment(tripId));
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
                        setData(tripData);
                        if (data.isActive) hideAndShowIcons(tripData.trip_status_id);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
