package com.example.dayout_organizer.ui.fragments.trips.CreateTrip;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.L;
import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.CreateTripPlaceAdapter;
import com.example.dayout_organizer.adapter.recyclers.CreateTripTypeAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.trip.Trip;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.ui.dialogs.PickPlaceDialog;
import com.example.dayout_organizer.ui.dialogs.PickTripTypeDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;


public class CreateTripTypeFragment extends Fragment {

    @BindView(R.id.pick_place_btn)
    Button pickPlaceButton;
    @BindView(R.id.pick_place_rc)
    RecyclerView pickPlaceRc;
    @BindView(R.id.next_btn)
    Button nextButton;


    CreateTripTypeAdapter createTripPlaceAdapter;
    PickTripTypeDialog tripTypeDialog;

    LoadingDialog loadingDialog;

    View view;
    Trip trip;
    public CreateTripTypeFragment(Trip trip) {
        this.trip = trip;
    }

    private void getDataFromApi(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_create_trip_type, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        loadingDialog = new LoadingDialog(requireContext());
        pickPlaceButton.setOnClickListener(onPickClicked);
        tripTypeDialog = new PickTripTypeDialog(requireContext(),trip.data.id);
        tripTypeDialog.setOnCancelListener(onCancelListener);
        nextButton.setOnClickListener(onNextClicked);
        initRc();
    }


    private void initRc() {
        pickPlaceRc.setHasFixedSize(true);
        pickPlaceRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        createTripPlaceAdapter = new CreateTripTypeAdapter(new ArrayList<>(), requireContext());
        pickPlaceRc.setAdapter(createTripPlaceAdapter);
    }

    private final View.OnClickListener onPickClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tripTypeDialog.show();
        }
    };

    private final View.OnClickListener onNextClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInfo()){
                loadingDialog.show();
                TripViewModel.getINSTANCE().createTripType(tripTypeDialog.getCreateTripType());
                TripViewModel.getINSTANCE().createTripMutableLiveData.observe(requireActivity(),tripObserver);
            }
        }
    };

    private final Observer<Pair<Trip,String>> tripObserver = new Observer<Pair<Trip, String>>() {
        @Override
        public void onChanged(Pair<Trip, String> tripStringPair) {
            loadingDialog.dismiss();
            if (tripStringPair != null){
                if (tripStringPair.first != null){
                    FN.addFixedNameFadeFragment(MAIN_FRC,requireActivity(),new CreateImageTripFragment(tripStringPair.first));
                }
                else {
                    new ErrorDialog(requireContext(),tripStringPair.second).show();
                }
            }
            else {
                new ErrorDialog(requireContext(),"Error Connection").show();
            }
        }
    };

    private final DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {

        }
    };

    private boolean checkInfo(){
        if (createTripPlaceAdapter.getItemCount() > 0){
            return true;
        }
        else {
            NoteMessage.showSnackBar(requireActivity(),"There are no places selected");
            return false;
        }
    }


}