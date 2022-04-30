package com.example.dayout_organizer.ui.fragments.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.BioDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

@SuppressLint("NonConstantResourceId")
public class ProfileFragment extends Fragment {

    View view;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.back_arrow_btn)
    ImageButton backArrowButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.profile_edit_button)
    ImageButton profileEditButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.profile_bio)
    TextView profileBio;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.profile_add_bio_icon)
    ImageButton addBioButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.profile_followers_count)
    TextView profileFollowersCount;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.profile_trips_count)
    TextView profileTripsCount;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.profile_gender)
    TextView profileGender;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.profile_phone_number)
    TextView profilePhoneNumber;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.profile_email)
    TextView profileEmail;



    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity)requireActivity()).hideBottomBar();
        super.onStart();
    }

    private final View.OnClickListener onBackArrowClicked = view -> FN.popTopStack(requireActivity());

    private final View.OnClickListener onAddBioClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BioDialog bioDialog = new BioDialog();
            bioDialog.show(requireActivity().getSupportFragmentManager(), "Bio Dialog");

        }
    };

    private final View.OnClickListener onEditProfileClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new EditProfileFragment());
        }
    };

    private void initViews() {
        backArrowButton.setOnClickListener(onBackArrowClicked);
        profileBio.setOnClickListener(onAddBioClicked);
        profileEditButton.setOnClickListener(onEditProfileClicked);
    }

}