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

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.CreateTripTypeAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.tripType.TripType;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.ui.dialogs.pick.PickTripTypeDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;


public class CreateTripTypeFragment extends Fragment {

    @BindView(R.id.pick_type_btn)
    Button pickPlaceButton;
    @BindView(R.id.pick_type_rc)
    RecyclerView pickPlaceRc;
    @BindView(R.id.next_btn)
    Button nextButton;


    CreateTripTypeAdapter createTripTypeAdapter;
    PickTripTypeDialog tripTypeDialog;

    LoadingDialog loadingDialog;

    View view;
    TripData tripData;
    public CreateTripTypeFragment(TripData tripData) {
        this.tripData = tripData;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_create_trip_type, container, false);
        ButterKnife.bind(this,view);
        initView();
        getDataFromApi();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity)requireActivity()).hideBottomBar();
        super.onStart();
    }

    private void initView() {
        loadingDialog = new LoadingDialog(requireContext());
        pickPlaceButton.setOnClickListener(onPickClicked);
        tripTypeDialog = new PickTripTypeDialog(requireContext(), tripData.id);
        tripTypeDialog.setOnCancelListener(onCancelListener);
        nextButton.setOnClickListener(onNextClicked);
        initRc();
    }

    private void initRc() {
        pickPlaceRc.setHasFixedSize(true);
        pickPlaceRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        createTripTypeAdapter = new CreateTripTypeAdapter(new ArrayList<>(), requireContext());
        createTripTypeAdapter.setOnItemClick(onItemClick);
        pickPlaceRc.setAdapter(createTripTypeAdapter);
    }

    private final CreateTripTypeAdapter.OnItemClick onItemClick = new CreateTripTypeAdapter.OnItemClick() {
        @Override
        public void OnCreateTripTypeItemClicked(int position, List<TripType> list) {
            tripTypeDialog.getCreateTripType().types.remove(list.get(position));
        }
    };

    private void getDataFromApi(){
        TripViewModel.getINSTANCE().getTripType();
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

    private final Observer<Pair<TripDetailsModel,String>> tripObserver = new Observer<Pair<TripDetailsModel, String>>() {
        @Override
        public void onChanged(Pair<TripDetailsModel, String> tripStringPair) {
            loadingDialog.dismiss();
            if (tripStringPair != null){
                if (tripStringPair.first != null){
                    FN.addFixedNameFadeFragment(MAIN_FRC,requireActivity(),new CreateImageTripFragment(tripStringPair.first.data));
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
            createTripTypeAdapter.refresh(tripTypeDialog.getCreateTripType().types);
        }
    };

    private boolean checkInfo(){
        if (createTripTypeAdapter.getItemCount() > 0){
            return true;
        }
        else {
            NoteMessage.showSnackBar(requireActivity(),"There are no places selected");
            return false;
        }
    }


}