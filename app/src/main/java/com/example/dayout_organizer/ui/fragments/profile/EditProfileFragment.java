package com.example.dayout_organizer.ui.fragments.profile;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.helpers.system.HttpRequestConverter;
import com.example.dayout_organizer.helpers.system.PermissionsHelper;
import com.example.dayout_organizer.helpers.view.ConverterImage;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.ImageViewer;

import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.profile.ProfileModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.viewModels.UserViewModel;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.example.dayout_organizer.helpers.view.ImageViewer.IMAGE_BASE_URL;


@SuppressLint("NonConstantResourceId")
public class EditProfileFragment extends Fragment {

    View view;

    @BindView(R.id.edit_profile_back_button)
    ImageButton editProfileBackButton;

    @BindView(R.id.edit_profile_done)
    TextView editProfileDone;

    @BindView(R.id.edit_profile_image)
    ImageView editProfileImage;

    @BindView(R.id.edit_profile_edit_button)
    ImageButton editProfileEditButton;

    @BindView(R.id.edit_profile_first_name)
    EditText editProfileFirstName;

    @BindView(R.id.edit_profile_last_name)
    EditText editProfileLastName;

    @BindView(R.id.edit_profile_email)
    EditText editProfileEmail;

    @BindView(R.id.edit_profile_bio)
    EditText editProfileBio;

    @BindView(R.id.edit_profile_delete_photo_button)
    ImageButton editProfileDeletePhotoButton;

    ProfileModel profileModel;

    LoadingDialog loadingDialog;

    Uri userImage;

    public EditProfileFragment(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);
        initViews();
        setData();

        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity) requireActivity()).hideBottomBar();
        super.onStart();
    }

    @Override
    public void onStop() {
        ((MainActivity) requireActivity()).showBottomBar();
        super.onStop();
    }

    private void initViews() {
        editProfileEditButton.setOnClickListener(onEditImageClicked);
        editProfileDeletePhotoButton.setOnClickListener(onDeleteImageClicked);
        editProfileBackButton.setOnClickListener(onBackClicked);
        editProfileDone.setOnClickListener(onDoneClicked);
        loadingDialog = new LoadingDialog(requireContext());
        if(editProfileImage.getDrawable() == getResources().getDrawable(R.drawable.profile_place_holder_orange))
            editProfileDeletePhotoButton.setVisibility(View.GONE);
    }

    private boolean checkInfo() {

        boolean firstNameValidation = isFirstNameValid();
        boolean lastNameValidation = isLastNameValid();
        boolean emailValidation = isEmailValid();

        return firstNameValidation && lastNameValidation && emailValidation;
    }

    private boolean isFirstNameValid() {

        Matcher en_firstNameMatcher = AppConstants.EN_NAME_REGEX.matcher(editProfileFirstName.getText().toString());
        Matcher ar_firstNameMatcher = AppConstants.AR_NAME_REGEX.matcher(editProfileFirstName.getText().toString());

        boolean ok = true;


        if (editProfileFirstName.getText().toString().isEmpty()) {
            editProfileFirstName.setError(getResources().getString(R.string.empty_field));

            ok = false;

        } else if (editProfileFirstName.getText().toString().charAt(0) == ' ') {
            editProfileFirstName.setError(getResources().getString(R.string.no_space));

            ok = false;

        } else if (!en_firstNameMatcher.matches()) {
            editProfileFirstName.setError(getResources().getString(R.string.name_does_not_match));

            ok = false;
        }
        else if (!ar_firstNameMatcher.matches()) {
            editProfileFirstName.setError(getResources().getString(R.string.name_does_not_match));

            ok = false;
        }

        return ok;
    }

    private boolean isLastNameValid() {

        Matcher en_lastNameMatcher = AppConstants.EN_NAME_REGEX.matcher(editProfileLastName.getText().toString());
        Matcher ar_lastNameMatcher = AppConstants.AR_NAME_REGEX.matcher(editProfileLastName.getText().toString());

        boolean ok = true;

        if (editProfileLastName.getText().toString().isEmpty()) {
            editProfileLastName.setError(getResources().getString(R.string.empty_field));

            ok = false;

        } else if (editProfileLastName.getText().toString().charAt(0) == ' ') {
            editProfileLastName.setError(getResources().getString(R.string.no_space));

            ok = false;

        } else if (!en_lastNameMatcher.matches()) {
            editProfileLastName.setError(getResources().getString(R.string.name_does_not_match));

            ok = false;
        }else if (!ar_lastNameMatcher.matches()) {
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

    private void selectImage() {
        if (PermissionsHelper.getREAD_EXTERNAL_STORAGE(requireActivity()))
            launcher.launch("image/*");
    }

    private final ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            editProfileImage.setImageURI(result);
            userImage = result;
        }
    });

    private void setData() {
        editProfileFirstName.setText(profileModel.data.user.first_name);
        editProfileLastName.setText(profileModel.data.user.last_name);
        editProfileEmail.setText(profileModel.data.user.email);
        editProfileBio.setText(profileModel.data.bio);
        downloadUserImage(profileModel.data.user.photo);
    }

    private void downloadUserImage(String url) {

        ImageViewer.downloadCircleImage(requireContext(), editProfileImage, R.drawable.profile_place_holder_orange, IMAGE_BASE_URL + url);
    }


    private final View.OnClickListener onEditImageClicked = view -> selectImage();

    private final View.OnClickListener onDeleteImageClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(profileModel.data.user.photo == null || profileModel.data.user.photo.isEmpty()){
                NoteMessage.showSnackBar(requireActivity(),getResources().getString(R.string.cannot_delete_profile_image));
            }
            else {
                loadingDialog.show();
                UserViewModel.getINSTANCE().deleteUserProfilePhoto();
                UserViewModel.getINSTANCE().successfulPairMutableLiveData.observe(requireActivity(),deleteProfileObserver);
            }
        }
    };

    private final Observer<Pair<Boolean,String>> deleteProfileObserver = new Observer<Pair<Boolean, String>>() {
        @Override
        public void onChanged(Pair<Boolean, String> booleanStringPair) {
            loadingDialog.dismiss();
            if (booleanStringPair != null){
                if (booleanStringPair.first != null){
                    editProfileImage.setBackgroundResource(R.drawable.profile_place_holder_orange);
                    NoteMessage.showSnackBar(requireActivity(),getResources().getString(R.string.delete_profile_image));
                }
                else {
                    new ErrorDialog(requireContext(),booleanStringPair.second).show();
                }
            }
            else{
                new ErrorDialog(requireContext(),getResources().getString(R.string.error_connection)).show();
            }
        }
    };

    private final View.OnClickListener onBackClicked = view -> FN.popStack(requireActivity());

    private final View.OnClickListener onDoneClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkInfo()) {
                loadingDialog.show();
                UserViewModel.getINSTANCE().editProfile(getRequestBody("PUT"),
                        getRequestBody(editProfileFirstName.getText().toString()),
                        getRequestBody(editProfileLastName.getText().toString()),
                        getRequestBody(editProfileBio.getText().toString()),
                        getRequestBody(editProfileEmail.getText().toString()),
                        getPhotoAsRequestBody());
                UserViewModel.getINSTANCE().successfulPairMutableLiveData.observe(requireActivity(),editProfileObserver);
            }
        }
    };

    private RequestBody getRequestBody(String body) {
        return HttpRequestConverter.createStringAsRequestBody("multipart/form-data", body);
    }

    private MultipartBody.Part getPhotoAsRequestBody(){

        if(userImage == null) return null;

        String path = ConverterImage.createImageFilePath(requireActivity(),userImage);
        File file = new File(path);
        RequestBody photoBody = HttpRequestConverter.createFileAsRequestBody("multipart/form-data",file);
        return HttpRequestConverter.createFormDataFile("photo",file.getName(),photoBody);
    }


    private final Observer<Pair<Boolean, String>> editProfileObserver = new Observer<Pair<Boolean, String>>() {
        @Override
        public void onChanged(Pair<Boolean, String> editProfileModelStringPair) {
            loadingDialog.dismiss();
            if (editProfileModelStringPair != null) {
                if (editProfileModelStringPair.first != null) {
                    FN.popStack(requireActivity());
                } else
                    new ErrorDialog(requireContext(), editProfileModelStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), getResources().getString(R.string.error_connection)).show();
        }
    };

}
