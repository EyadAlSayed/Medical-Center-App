package com.example.dayout_organizer.ui.fragments.drawer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;


import com.example.dayout_organizer.R;
import com.example.dayout_organizer.config.AppSharedPreferences;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.ui.fragments.home.HomeFragment;
import com.example.dayout_organizer.viewModels.PlaceViewModel;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;
import static com.example.dayout_organizer.config.AppSharedPreferences.GET_USER_ID;

@SuppressLint("NonConstantResourceId")
public class SuggestionFragment extends Fragment {


    View view;

    @BindView(R.id.arrow_back)
    ImageButton arrowBack;

    @BindView(R.id.place_name)
    EditText placeName;

    @BindView(R.id.place_address)
    EditText placeAddress;

    @BindView(R.id.place_desc)
    EditText placeDesc;

    @BindView(R.id.send_btn)
    Button sendButton;

    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_suggestion, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity) requireActivity()).hideDrawerButton();
        super.onStart();
    }

    private void initView() {
        loadingDialog = new LoadingDialog(requireContext());
        arrowBack.setOnClickListener(v -> FN.popTopStack(requireActivity()));
        sendButton.setOnClickListener(onSendClicked);
    }

    private final View.OnClickListener onSendClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInfo()) {
                loadingDialog.show();
                PlaceViewModel.getINSTANCE().suggestPlace(getPlaceObj());
                PlaceViewModel.getINSTANCE().successfulMutableLiveData.observe(requireActivity(), succesfulObserver);
            }
            else {
                NoteMessage.showSnackBar(requireActivity(),getResources().getString(R.string.info_not_complete));
            }
        }
    };

    private final Observer<Pair<Boolean, String>> succesfulObserver = booleanStringPair -> {
        loadingDialog.dismiss();
        if (booleanStringPair != null) {
            if (booleanStringPair.first != null) {
                NoteMessage.showSnackBar(requireActivity(), getResources().getString(R.string.suggested));
                FN.replaceFadeFragment(MAIN_FRC,requireActivity(),new HomeFragment());
            } else {
                new ErrorDialog(requireContext(), booleanStringPair.second).show();
            }
        } else {
            new ErrorDialog(requireContext(), getResources().getString(R.string.error_connection)).show();
        }
    };

    private JsonObject getPlaceObj(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("organizer_id", GET_USER_ID());
        jsonObject.addProperty("place_name", placeName.getText().toString());
        jsonObject.addProperty("place_address", placeAddress.getText().toString());
        jsonObject.addProperty("description",placeDesc.getText().toString());
        return jsonObject;
    }


    private boolean checkInfo() {
        boolean ok = true;

        if (placeName.getText().toString().isEmpty()) {
            ok = false;
        }
        if (placeAddress.getText().toString().isEmpty()) {
            ok = false;
        }
        if (placeDesc.getText().toString().isEmpty()) {
            ok = false;
        }
        return ok;
    }

}