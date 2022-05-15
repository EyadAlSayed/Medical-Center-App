package com.example.dayout_organizer.ui.fragments.trips.CreateTrip;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.CreateTripPlaceAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.ui.dialogs.PickPlaceDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

public class CreateTripPlaceFragment extends Fragment {


    View view;
    @BindView(R.id.pick_place_btn)
    Button pickPlaceButton;
    @BindView(R.id.pick_place_rc)
    RecyclerView pickPlaceRc;
    @BindView(R.id.next_btn)
    Button nextButton;


    CreateTripPlaceAdapter createTripPlaceAdapter;
    PickPlaceDialog pickPlaceDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_create_trip_place, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        pickPlaceButton.setOnClickListener(onPickClicked);
        pickPlaceDialog = new PickPlaceDialog(requireContext());
        pickPlaceDialog.setOnCancelListener(onCancelListener);
        nextButton.setOnClickListener(onNextClicked);
        initRc();
    }

    private final View.OnClickListener onNextClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if (checkInfo())
            FN.addFixedNameFadeFragment(MAIN_FRC,requireActivity(),new CreateImageTripFragment());
        }
    };

    private void initRc() {
        pickPlaceRc.setHasFixedSize(true);
        pickPlaceRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        createTripPlaceAdapter = new CreateTripPlaceAdapter(new ArrayList<>(), requireContext());
        pickPlaceRc.setAdapter(createTripPlaceAdapter);
    }

    private final View.OnClickListener onPickClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pickPlaceDialog.show();
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