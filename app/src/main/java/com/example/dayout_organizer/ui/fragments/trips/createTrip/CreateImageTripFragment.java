package com.example.dayout_organizer.ui.fragments.trips.createTrip;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.system.HttpRequestConverter;
import com.example.dayout_organizer.helpers.system.PermissionsHelper;
import com.example.dayout_organizer.helpers.view.ConverterImage;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.ui.fragments.home.HomeFragment;
import com.example.dayout_organizer.viewModels.TripViewModel;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

@SuppressLint("NonConstantResourceId")
public class CreateImageTripFragment extends Fragment {


    View view;

    @BindView(R.id.select_image_btn)
    Button selectImageButton;

    @BindView(R.id.select_img)
    ImageView selectImg;

    @BindView(R.id.previous_btn)
    Button previousButton;

    @BindView(R.id.next_btn)
    Button nextButton;

    @BindView(R.id.create_btn)
    Button createButton;

    @BindView(R.id.cancel_button)
    ImageButton cancelButton;


    List<Uri> uris;

    int uriIdx;

    LoadingDialog loadingDialog;
    TripData tripData;

    public CreateImageTripFragment(TripData tripData) {
        this.tripData = tripData;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_create_image_trip, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity) requireActivity()).hideBottomBar();
        super.onStart();
    }


    private void initView() {

        uriIdx = 0;
        uris = new ArrayList<>();

        loadingDialog = new LoadingDialog(requireContext());


        selectImageButton.setOnClickListener(onSelectImageClicked);
        previousButton.setOnClickListener(onPreviousClicked);
        nextButton.setOnClickListener(onNextClicked);
        createButton.setOnClickListener(onCreateClicked);
        cancelButton.setOnClickListener(onCancelClicked);
    }

    private final View.OnClickListener onSelectImageClicked = v -> pickImage();

    private final View.OnClickListener onCancelClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (uris.size() > 0 && uriIdx >= 0 && uriIdx < uris.size()) {

                if (uris.size() == 1) selectImg.setImageURI(Uri.EMPTY);
                else if (uriIdx == 0) selectImg.setImageURI(uris.get(uriIdx + 1));
                else selectImg.setImageURI(uris.get(uriIdx - 1));

                uris.remove(uriIdx);

            }
        }
    };

    private final View.OnClickListener onPreviousClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (uriIdx - 1 >= 0 && uriIdx - 1 < uris.size()) {
                selectImg.setImageURI(uris.get(uriIdx - 1));
                uriIdx--;
            }
        }
    };

    private final View.OnClickListener onNextClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (uriIdx+1 >= 0 && uriIdx+1 < uris.size()) {
                selectImg.setImageURI(uris.get(uriIdx+1));
                uriIdx++;
            }
        }
    };

    private final View.OnClickListener onCreateClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInfo()) {
                loadingDialog.show();
                TripViewModel.getINSTANCE().createTripPhoto(getIdRequestBody(),getPhotos());
                TripViewModel.getINSTANCE().createTripMutableLiveData.observe(requireActivity(), tripObserver);
            }
        }
    };

    private final Observer<Pair<TripDetailsModel, String>> tripObserver = new Observer<Pair<TripDetailsModel, String>>() {
        @Override
        public void onChanged(Pair<TripDetailsModel, String> tripStringPair) {
            loadingDialog.dismiss();
            if (tripStringPair != null) {
                if (tripStringPair.first != null) {
                    NoteMessage.showSnackBar(requireActivity(), "Successfully Added");
                    FN.popAllStack(requireActivity());
                    FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new HomeFragment());

                } else {
                    new ErrorDialog(requireContext(), tripStringPair.second).show();
                }
            } else {
                new ErrorDialog(requireContext(), getResources().getString(R.string.error_connection)).show();
            }
        }
    };

    private void pickImage() {
        if (PermissionsHelper.getREAD_EXTERNAL_STORAGE(requireActivity()))
            launcher.launch("image/*");
    }

    private final ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            uris.add(result);
            uriIdx = uris.size() - 1;
            selectImg.setImageURI(result);
        }
    });



    private boolean checkInfo() {
        if (uris.size() > 0) {
            return true;
        } else {
            NoteMessage.showSnackBar(requireActivity(), "There is no photo selected");
            return false;
        }
    }

}