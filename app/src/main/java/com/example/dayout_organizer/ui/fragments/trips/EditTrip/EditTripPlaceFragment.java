package com.example.dayout_organizer.ui.fragments.trips.EditTrip;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.CreateTripPlaceAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.trip.PlaceTripData;
import com.example.dayout_organizer.models.trip.SingleTripModel;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.ui.dialogs.PickPlaceDialog;
import com.example.dayout_organizer.viewModels.PlaceViewModel;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

public class EditTripPlaceFragment extends Fragment {

    View view;
    @BindView(R.id.pick_place_btn)
    Button pickPlaceButton;
    @BindView(R.id.pick_place_rc)
    RecyclerView pickPlaceRc;
    @BindView(R.id.next_btn)
    Button nextButton;


    CreateTripPlaceAdapter createTripPlaceAdapter;
    PickPlaceDialog pickPlaceDialog;

    TripData data;

    LoadingDialog loadingDialog;


    public EditTripPlaceFragment(TripData data) {
        this.data = data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_create_trip_place, container, false);
        ButterKnife.bind(this, view);
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
        pickPlaceDialog = new PickPlaceDialog(requireContext(),data.id);
        pickPlaceDialog.setOnCancelListener(onCancelListener);
        nextButton.setOnClickListener(onNextClicked);
        initRc(data.place_trips);

    }

    private void initRc(List<PlaceTripData> place_trips) {
        pickPlaceRc.setHasFixedSize(true);
        pickPlaceRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        createTripPlaceAdapter = new CreateTripPlaceAdapter(place_trips, requireContext());
        createTripPlaceAdapter.setOnItemClick(onItemClick);
        pickPlaceRc.setAdapter(createTripPlaceAdapter);
    }

    private void getDataFromApi(){
        PlaceViewModel.getINSTANCE().getPlaces();
    }

    private final View.OnClickListener onNextClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInfo()){
                loadingDialog.show();
                TripViewModel.getINSTANCE().editTripPlaces(pickPlaceDialog.getCreateTripPlace());
                TripViewModel.getINSTANCE().createTripMutableLiveData.observe(requireActivity(),tripObserver);
            }
        }
    };

    private final Observer<Pair<SingleTripModel,String>> tripObserver = new Observer<Pair<SingleTripModel, String>>() {
        @Override
        public void onChanged(Pair<SingleTripModel, String> tripStringPair) {
            loadingDialog.dismiss();
            if (tripStringPair != null){
                if (tripStringPair.first != null){
                    FN.addFixedNameFadeFragment(MAIN_FRC,requireActivity(),new EditTripTypeFragment(data));
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

    private final CreateTripPlaceAdapter.OnItemClick onItemClick = new CreateTripPlaceAdapter.OnItemClick() {
        @Override
        public void OnCreateTripPlaceItemClicked(int position, List<PlaceTripData> list) {
            pickPlaceDialog.getCreateTripPlace().places.remove(list.get(position));
        }
    };

    private final View.OnClickListener onPickClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pickPlaceDialog.show();
        }
    };

    private final DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            createTripPlaceAdapter.refresh(pickPlaceDialog.getCreateTripPlace().places);
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