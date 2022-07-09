package com.example.dayout_organizer.ui.fragments.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.ImageViewer;
import com.example.dayout_organizer.models.profile.ProfileData;
import com.example.dayout_organizer.models.profile.ProfileModel;
import com.example.dayout_organizer.room.profileRoom.databases.ProfileDatabase;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.BioDialog;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.viewModels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.dayout_organizer.api.ApiClient.BASE_URL;
import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;
import static com.example.dayout_organizer.config.AppSharedPreferences.GET_USER_ID;

@SuppressLint("NonConstantResourceId")
public class ProfileFragment extends Fragment {

    private final String TAG = "ProfileFragment";

    View view;

    @BindView(R.id.back_arrow_btn)
    ImageButton backArrowButton;

    @BindView(R.id.profile_edit_button)
    ImageButton profileEditButton;

    @BindView(R.id.profile_image)
    ImageView profileImage;

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

    @BindView(R.id.profile_rate)
    TextView profileRate;

    LoadingDialog loadingDialog;

    ProfileModel profileModel;

    BioDialog bioDialog;

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

    private void getDataFromRoom(){
        ProfileDatabase.getINSTANCE(requireContext())
                .iProfileModel()
                .getProfile(GET_USER_ID())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ProfileData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ProfileData profileData) {
                        setData(profileData);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
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
                    profileModel = profileModelStringPair.first;
                    bioDialog = new BioDialog(requireContext(), profileModel);
                } else {
                    getDataFromRoom();
                    new ErrorDialog(requireContext(), profileModelStringPair.second).show();
                }
            } else {
                getDataFromRoom();
                new ErrorDialog(requireContext(), getResources().getString(R.string.error_connection)).show();
            }
        }
    };

    private void setData(ProfileData data) {
        setName(data.user.first_name, data.user.last_name);
        if (data.bio != null)
            setBio(data.bio);
        profileTripsCount.setText(String.valueOf(data.trips_count));
        profileFollowersCount.setText(String.valueOf(data.followers_count));
        profileGender.setText(data.user.gender);
        profilePhoneNumber.setText(data.user.phone_number);
        profileRate.setText(String.valueOf(roundRating(data.rating)));
        setEmail(data.user.email);
        downloadUserImage(data.user.photo);
    }

    private void downloadUserImage(String url){
        String baseUrl = BASE_URL.substring(0,BASE_URL.length()-1);
        ImageViewer.downloadCircleImage(requireContext(),profileImage,R.drawable.profile_place_holder,baseUrl+url);
    }

    private float roundRating(float rating){
        return (float) (Math.round(rating * 10) / 10.0);
    }

    @SuppressLint("SetTextI18n")
    private void setName(String firstName, String lastName) {
        profileFullName.setText(firstName + " " + lastName);
    }

    private void setEmail(String email) {
        if (email == null) {
            profileEmail.setVisibility(View.GONE);
            emailIcon.setVisibility(View.GONE);
            emailTV.setVisibility(View.GONE);
        } else
            profileEmail.setText(email);
    }

    private void setBio(String bio) {
        if (bio != null) {
            profileBio.setText(bio);
            addBioButton.setVisibility(View.GONE);
            profileBio.setClickable(false);
        } else {
            profileBio.setText(R.string.biography);
            addBioButton.setVisibility(View.VISIBLE);
            profileBio.setClickable(true);
        }
    }


    private final View.OnClickListener onBackArrowClicked = view -> FN.popTopStack(requireActivity());

     private final View.OnClickListener onAddBioClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BioDialog dialog = new BioDialog(requireContext(), profileModel);
            dialog.setOnCancelListener(dialogInterface -> setBio(dialog.getBioString()));
            dialog.show();
        }

    };

    private final View.OnClickListener onEditProfileClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new EditProfileFragment(profileModel));
        }
    };

}