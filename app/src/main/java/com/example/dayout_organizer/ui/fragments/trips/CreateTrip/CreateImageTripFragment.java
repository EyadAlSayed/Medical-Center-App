package com.example.dayout_organizer.ui.fragments.trips.CreateTrip;

import android.net.Uri;
import android.os.Bundle;
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

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.system.PermissionsHelper;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


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


    ArrayList<Uri> uris;
    int uriIdx;

    LoadingDialog loadingDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_create_image_trip, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
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
            if (uris.size() > 0) uris.remove(uriIdx);
        }
    };

    private final View.OnClickListener onPreviousClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (uriIdx >= 0 && uriIdx < uris.size()) {
                selectImg.setImageURI(uris.get(uriIdx));
                uriIdx--;
            }
        }
    };

    private final View.OnClickListener onNextClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (uriIdx >= 0 && uriIdx < uris.size()) {
                selectImg.setImageURI(uris.get(uriIdx));
                uriIdx++;
            }
        }
    };

    private final View.OnClickListener onCreateClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInfo()) {
                loadingDialog.show();
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
            uris.add(result);            //Send this string to Backend.
            selectImg.setImageURI(result);
        }
    });

    private boolean checkInfo() {
        if (uris.size() > 0){
            return true;
        }
        else {
            NoteMessage.showSnackBar(requireActivity(),"There is no photo selected");
            return false;
        }
    }

}