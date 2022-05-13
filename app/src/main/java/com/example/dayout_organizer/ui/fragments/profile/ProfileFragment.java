package com.example.dayout_organizer.ui.fragments.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.ImageViewer;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.ProfileModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.BioDialog;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.viewModels.UserViewModel;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;
import static com.example.dayout_organizer.config.AppSharedPreferences.GET_USER_ID;
import static com.example.dayout_organizer.viewModels.UserViewModel.USER_PHOTO_URL;

@SuppressLint("NonConstantResourceId")
public class ProfileFragment extends Fragment {

    private final String TAG = "ProfileFragment";

    View view;

    @BindView(R.id.back_arrow_btn)
    ImageButton backArrowButton;

    @BindView(R.id.profile_edit_button)
    ImageButton profileEditButton;

    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    @BindView(R.id.profile_bio)
    TextView profileBio;

    @BindView(R.id.profile_add_bio_icon)
    ImageButton addBioButton;

    @BindView(R.id.profile_followers_count)
    TextView profileFollowersCount;

    @BindView(R.id.profile_trips_count)
    TextView profileTripsCount;

    @BindView(R.id.profile_gender)
    TextView profileGender;

    @BindView(R.id.profile_phone_number)
    TextView profilePhoneNumber;

    @BindView(R.id.profile_email)
    TextView profileEmail;

    @BindView(R.id.profile_full_name)
    TextView profileFullName;

    @BindView(R.id.profile_email_TV)
    TextView emailTV;

    @BindView(R.id.profile_email_icon)
    ImageButton emailIcon;

    LoadingDialog loadingDialog;

    ProfileModel.Data profileModelData;

    BioDialog bioDialog;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromAPI();
        return view;
    }

    @Override
    public void onStart() {
        loadingDialog.show();
        ((MainActivity) requireActivity()).hideBottomBar();
        super.onStart();
    }

    private void initViews() {


        backArrowButton.setOnClickListener(onBackArrowClicked);
        profileBio.setOnClickListener(onAddBioClicked);
        profileEditButton.setOnClickListener(onEditProfileClicked);
        loadingDialog = new LoadingDialog(requireContext());
    }

    private void getDataFromAPI() {
        UserViewModel.getINSTANCE().getOrganizerProfile(GET_USER_ID());
        UserViewModel.getINSTANCE().profileMutableLiveData.observe(requireActivity(), profileObserver);
    }

    private final Observer<Pair<ProfileModel, String>> profileObserver = new Observer<Pair<ProfileModel, String>>() {
        @Override
        public void onChanged(Pair<ProfileModel, String> profileModelStringPair) {
            loadingDialog.dismiss();
            if (profileModelStringPair != null) {
                if (profileModelStringPair.first != null) {
                    setData(profileModelStringPair.first.data);
                    profileModelData = profileModelStringPair.first.data;
                    bioDialog = new BioDialog(requireContext(), profileModelData);
                    bioDialog.setOnCancelListener(onBioDialogCancel);
                } else
                    new ErrorDialog(requireContext(), profileModelStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

    private void setData(ProfileModel.Data data) {
        setName(data.user.first_name, data.user.last_name);
        if (data.bio != null) setBio(data.bio);
        profileTripsCount.setText(String.valueOf(data.trips_count));
        profileFollowersCount.setText(String.valueOf(data.followers_count));
        profileGender.setText(data.user.gender);
        profilePhoneNumber.setText(data.user.phone_number);
        setEmail(data.user.email);
        downloadUserImage(data.id);
    }

    private void setEmail(String email) {
        if (email == null) {
            profileEmail.setVisibility(View.GONE);
            emailIcon.setVisibility(View.GONE);
            emailTV.setVisibility(View.GONE);
        } else
            profileEmail.setText(email);
    }

    private void downloadUserImage(int id){
        ImageViewer.downloadImage(requireContext(),profileImage,R.drawable.ic_user_profile,USER_PHOTO_URL+id);
    }

    @SuppressLint("SetTextI18n")
    private void setName(String firstName, String lastName) {
        profileFullName.setText(firstName + " " + lastName);
    }

    private void setBio(String bio) {
        profileBio.setText(bio);
        addBioButton.setVisibility(View.GONE);
    }

    private final View.OnClickListener onBackArrowClicked = view -> FN.popTopStack(requireActivity());

    private final View.OnClickListener onAddBioClicked = view -> {

        bioDialog.show();
    };

    private final DialogInterface.OnCancelListener onBioDialogCancel = dialog -> {
        profileBio.setText(bioDialog.bioString);
    };

    private final View.OnClickListener onEditProfileClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new EditProfileFragment(profileModelData));
        }
    };

}