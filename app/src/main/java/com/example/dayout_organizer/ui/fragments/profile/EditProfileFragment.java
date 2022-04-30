package com.example.dayout_organizer.ui.fragments.profile;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.helpers.system.PermissionsHelper;
import com.example.dayout_organizer.helpers.view.ConverterImage;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;

import java.util.regex.Matcher;

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
        editProfileEditButton.setOnClickListener(onEditImageClicked);
        editProfileBackButton.setOnClickListener(onBackClicked);
        editProfileDone.setOnClickListener(onDoneClicked);
    }

    private boolean checkInfo(){

        boolean firstNameValidation = isFirstNameValid();
        boolean lastNameValidation = isLastNameValid();
        boolean emailValidation = isEmailValid();
        boolean phoneNumberValidation = isPhoneNumberValid();

        return firstNameValidation && lastNameValidation && emailValidation && phoneNumberValidation;
    }

    private boolean isFirstNameValid() {

        Matcher firstNameMatcher = AppConstants.NAME_REGEX.matcher(editProfileFirstName.getText().toString());

        boolean ok = true;


        if (editProfileFirstName.getText().toString().isEmpty()) {
            editProfileFirstName.setError(getResources().getString(R.string.empty_field));

            ok = false;

        } else if (editProfileFirstName.getText().toString().charAt(0) == ' ') {
            editProfileFirstName.setError(getResources().getString(R.string.no_space));

            ok = false;

        } else if (!firstNameMatcher.matches()) {
            editProfileFirstName.setError(getResources().getString(R.string.name_does_not_match));

            ok = false;
        }

        return ok;
    }

    private boolean isLastNameValid() {

        Matcher lastNameMatcher = AppConstants.NAME_REGEX.matcher(editProfileLastName.getText().toString());

        boolean ok = true;

        if (editProfileLastName.getText().toString().isEmpty()) {
            editProfileLastName.setError(getResources().getString(R.string.empty_field));

            ok = false;

        } else if (editProfileLastName.getText().toString().charAt(0) == ' ') {
            editProfileLastName.setError(getResources().getString(R.string.no_space));

            ok = false;

        } else if (!lastNameMatcher.matches()) {
            editProfileLastName.setError(getResources().getString(R.string.name_does_not_match));

            ok = false;
        }

        return ok;
    }

    private boolean isEmailValid() {

        Matcher emailMatcher = AppConstants.EMAIL_REGEX.matcher(editProfileEmail.getText().toString());

        boolean ok = true;

        if (!editProfileEmail.getText().toString().isEmpty()) {
            if (!emailMatcher.matches()) {
                editProfileEmail.setError(getResources().getString(R.string.not_an_email_address));

                ok = false;
            }
        }

        return ok;
    }

    private boolean isPhoneNumberValid() {

        Matcher phoneNumberMatcher = AppConstants.PHONE_NUMBER_REGEX.matcher(editProfilePhoneNumber.getText().toString());

        boolean ok = true;

        if (!phoneNumberMatcher.matches()) {
            editProfilePhoneNumber.setError(getResources().getString(R.string.not_a_phone_number));

            ok = false;
        }

        return ok;
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
        }
    });


    private final View.OnClickListener onEditImageClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectImage();
        }
    };

    private final View.OnClickListener onBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FN.popStack(requireActivity());
        }
    };

    private final View.OnClickListener onDoneClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO: Validate input - Caesar.
            if (checkInfo()) {
                System.out.println("VALID");
                //TODO: Send info to Backend - Caesar.
                FN.popStack(requireActivity());
            }
        }
    };

}
