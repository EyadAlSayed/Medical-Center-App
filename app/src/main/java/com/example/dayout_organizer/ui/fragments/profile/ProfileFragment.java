package com.example.dayout_organizer.ui.fragments.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.ui.dialogs.BioDialog;
import com.example.dayout_organizer.ui.fragments.drawer.DrawerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Looper.getMainLooper;
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

    private boolean isDrawerOpen = false;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private final View.OnClickListener onDrawerButtonClicked = view -> FN.popAllStack(requireActivity());

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

        }
    };

    private void initViews() {
        backArrowButton.setOnClickListener(onDrawerButtonClicked);
        profileBio.setOnClickListener(onAddBioClicked);
        profileEditButton.setOnClickListener(onEditProfileClicked);
    }

}
