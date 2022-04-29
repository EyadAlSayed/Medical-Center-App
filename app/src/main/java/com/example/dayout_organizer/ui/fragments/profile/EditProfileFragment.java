package com.example.dayout_organizer.ui.fragments.profile;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.system.PermissionsHelper;
import com.example.dayout_organizer.helpers.view.ConverterImage;
import com.example.dayout_organizer.helpers.view.FN;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("NonConstantResourceId")
public class EditProfileFragment extends Fragment {

    View view;

    String imageAsString;

    @BindView(R.id.edit_profile_back_button)
    ImageButton editProfileBackButton;

    @BindView(R.id.edit_profile_done)
    TextView editProfileDone;

    @BindView(R.id.edit_profile_upload_image_button)
    ImageButton editProfileUploadImageButton;

    @BindView(R.id.edit_profile_upload_image_layout)
    LinearLayout editProfileUploadImageLayout;

    @BindView(R.id.edit_profile_image)
    CircleImageView editProfileImage;

    @BindView(R.id.edit_profile_edit_button)
    ImageButton editProfileEditButton;

    @BindView(R.id.edit_profile_image_layout)
    LinearLayout editProfileImageLayout;

    @BindView(R.id.edit_profile_first_name)
    EditText editProfileFirstName;

    @BindView(R.id.edit_profile_last_name)
    EditText editProfileLastName;

    @BindView(R.id.edit_profile_phone_number)
    EditText editProfilePhoneNumber;

    @BindView(R.id.edit_profile_email)
    EditText editProfileEmail;

    public EditProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews(){
        editProfileUploadImageButton.setOnClickListener(onUploadImageClicked);
        editProfileEditButton.setOnClickListener(onUploadImageClicked);
        editProfileBackButton.setOnClickListener(onBackClicked);
        editProfileDone.setOnClickListener(onDoneClicked);
    }

    private void selectImage() {
        if (PermissionsHelper.getREAD_EXTERNAL_STORAGE(requireActivity()))
            launcher.launch("image/*");
    }

    private final ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            editProfileImage.setImageURI(result);
            //TODO Send this string to Backend - Caesar.
            imageAsString = ConverterImage.convertUriToBase64(requireContext(), result);
            if (imageAsString != null)
                adjustVisibilities();
        }
    });

    private void adjustVisibilities(){
        editProfileUploadImageLayout.setVisibility(View.GONE);
        editProfileImageLayout.setVisibility(View.VISIBLE);
    }


    private final View.OnClickListener onUploadImageClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectImage();
        }
    };

    private final View.OnClickListener onBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FN.popTopStack(requireActivity());
        }
    };

    private final View.OnClickListener onDoneClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO: Validate input - Caesar.
            //TODO: Send info to Backend - Caesar.
            FN.popTopStack(requireActivity());
        }
    };

}
