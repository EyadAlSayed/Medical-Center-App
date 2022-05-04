package com.example.dayout_organizer.ui.fragments.profile;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.ProfileModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.BioDialog;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.viewModels.UserViewModel;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

@SuppressLint("NonConstantResourceId")
public class ProfileFragment extends Fragment {

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
        ((MainActivity)requireActivity()).hideBottomBar();
        super.onStart();
    }

    private void initViews() {
        backArrowButton.setOnClickListener(onBackArrowClicked);
        profileBio.setOnClickListener(onAddBioClicked);
        profileEditButton.setOnClickListener(onEditProfileClicked);
    }

    private void getDataFromAPI(){
        UserViewModel.getINSTANCE().getOrganizerProfile();
        UserViewModel.getINSTANCE().profileMutableLiveData.observe(requireActivity(), profileObserver);
    }

    private final Observer<Pair<ProfileModel, String>> profileObserver = new Observer<Pair<ProfileModel, String>>() {
        @Override
        public void onChanged(Pair<ProfileModel, String> profileModelStringPair) {
            if(profileModelStringPair != null){
                if(profileModelStringPair.first != null){
                    setData(profileModelStringPair.first);
                } else
                    new ErrorDialog(requireContext(), profileModelStringPair.second);
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

    private void setData(ProfileModel model){
        setName(model.first_name, model.last_name);
        profileImage.setImageURI(Uri.parse(model.photo));
        setBio(model.bio);
        profileTripsCount.setText(String.valueOf(model.trips_count));
        profileFollowersCount.setText(String.valueOf(model.followers_count));
        profileGender.setText(model.gender);
        profilePhoneNumber.setText(model.phone_number);
        setEmail(model.email);
    }

    private void setEmail(String email){
        if(email == null){
            profileEmail.setVisibility(View.GONE);
            emailIcon.setVisibility(View.GONE);
            emailTV.setVisibility(View.GONE);
        } else
            profileEmail.setText(email);
    }

    private void setName(String firstName, String lastName){
        profileFullName.setText(firstName + " " + lastName);
    }

    private void setBio(String bio){
        profileBio.setText(bio);
        addBioButton.setVisibility(View.GONE);
    }

    private final View.OnClickListener onBackArrowClicked = view -> FN.popTopStack(requireActivity());

    private final View.OnClickListener onAddBioClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            BioDialog bioDialog = new BioDialog();
//            bioDialog.show(requireActivity().getSupportFragmentManager(), "Bio Dialog");
            new BioDialog(requireContext()).show();
        }
    };

    private final View.OnClickListener onEditProfileClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new EditProfileFragment());
        }
    };

}