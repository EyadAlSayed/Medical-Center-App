package com.example.dayout_organizer.ui.fragments.auth;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.system.PermissionsHelper;
import com.example.dayout_organizer.helpers.view.ConverterImage;
import com.example.dayout_organizer.helpers.view.FN;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.AUTH_FRC;


public class SignUpFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.signup_male)
    RadioButton maleRadioButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.signup_female)
    RadioButton femaleRadioButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_first_name)
    TextInputEditText firstName;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.signup_first_name_textlayout)
    TextInputLayout firstNameTextlayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_last_name)
    TextInputEditText lastName;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.signup_last_name_textlayout)
    TextInputLayout lastNameTextlayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_password)
    TextInputEditText password;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_password_textlayout)
    TextInputLayout passwordTextlayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_confirm_password)
    TextInputEditText confirmPassword;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_confirm_password_textlayout)
    TextInputLayout confirmPasswordTextlayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_phone_number)
    TextInputEditText phoneNumber;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_phone_number_textlayout)
    TextInputLayout phoneNumberTextlayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_button)
    Button signUpButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_to_login)
    TextView signUpToLogin;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_radio_group)
    RadioGroup radioGroup;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.signup_upload_image)
    ImageButton upload_image_button;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up_check_icon)
    ImageButton signUpCheckIcon;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.signup_edit_id_image)
    ImageButton signUpEditIdImage;

    View view;

    String imageAsString;

    private void selectImage() {
        if (PermissionsHelper.getREAD_EXTERNAL_STORAGE(requireActivity()))
            launcher.launch("image/*");
    }

    private final ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            //TODO Send this string to Backend - Caesar.
            imageAsString = ConverterImage.convertUriToBase64(requireContext(), result);
            if(imageAsString != null)
                adjustedVisibilities();
        }
    });

    public SignUpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        initView();

        if(imageAsString != null)
            adjustedVisibilities();

        return view;
    }

    private final View.OnClickListener onSignUpBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkInfo()) {
                //TODO: Send Object to Back - Caesar.
            }
        }
    };

    private final View.OnClickListener onUploadImageClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectImage();
        }
    };

    private final View.OnClickListener onEditImageClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            imageAsString = null;
            selectImage();
        }
    };


    private final View.OnClickListener onToLoginClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FN.addFixedNameFadeFragment(AUTH_FRC, requireActivity(), new LoginFragment());
        }
    };

    private boolean checkInfo() {

        boolean ok = true;

        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            confirmPasswordTextlayout.setErrorEnabled(true);
            confirmPasswordTextlayout.setError(getResources().getString(R.string.does_not_match_password));

            ok = false;
        }

        //FIXME: All Regex Not Working - Caesar.

//        Matcher firstNameMatcher = AppConstants.NAME_REGEX.matcher(firstName.getText().toString());
//        Matcher lastNameMatcher = AppConstants.NAME_REGEX.matcher(lastName.getText().toString());
//        Matcher phoneNumberMatcher = AppConstants.PHONE_NUMBER_REGEX.matcher(phoneNumber.getText().toString());

//        if (!firstNameMatcher.matches()) {
//            firstNameTextlayout.setErrorEnabled(true);
//            firstNameTextlayout.setError(getResources().getString(R.string.name_does_not_match));
//
//            ok = false;
//        }
//
//        if (!lastNameMatcher.matches()) {
//            lastNameTextlayout.setErrorEnabled(true);
//            lastNameTextlayout.setError(getResources().getString(R.string.name_does_not_match));
//
//            ok = false;
//        }
//
//        if (!phoneNumberMatcher.matches()) {
//            phoneNumberTextlayout.setErrorEnabled(true);
//            phoneNumberTextlayout.setError(getResources().getString(R.string.not_a_phone_number));
//
//            ok = false;
//        }

        return ok;

    }

    private final TextWatcher passwordConfirmationWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            confirmPasswordTextlayout.setErrorEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private final TextWatcher firstNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            firstNameTextlayout.setErrorEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private final TextWatcher lastNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            lastNameTextlayout.setErrorEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private final TextWatcher phoneNumberWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            phoneNumberTextlayout.setErrorEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void initView() {
        confirmPassword.addTextChangedListener(passwordConfirmationWatcher);
        firstName.addTextChangedListener(firstNameWatcher);
        lastName.addTextChangedListener(lastNameWatcher);
        phoneNumber.addTextChangedListener(phoneNumberWatcher);
        signUpToLogin.setOnClickListener(onToLoginClicked);
        signUpButton.setOnClickListener(onSignUpBtnClicked);
        upload_image_button.setOnClickListener(onUploadImageClicked);
        signUpEditIdImage.setOnClickListener(onEditImageClicked);
    }

    private void adjustedVisibilities(){
        upload_image_button.setVisibility(View.GONE);
        signUpEditIdImage.setVisibility(View.VISIBLE);
        signUpCheckIcon.setVisibility(View.VISIBLE);
    }
}