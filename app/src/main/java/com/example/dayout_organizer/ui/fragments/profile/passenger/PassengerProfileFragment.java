package com.example.dayout_organizer.ui.fragments.profile.passenger;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.ImageViewer;
import com.example.dayout_organizer.ui.dialogs.ReportDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.helpers.view.ImageViewer.IMAGE_BASE_URL;


@SuppressLint("NonConstantResourceId")
public class PassengerProfileFragment extends Fragment {

    View view;

    private static  final String TAG = "Profile Fragment";

    @BindView(R.id.passenger_back_arrow_btn)
    ImageButton backArrowButton;

    @BindView(R.id.passenger_profile_image)
    ImageView profileImage;

    @BindView(R.id.passenger_profile_following_count)
    TextView profileFollowingCount;

    @BindView(R.id.passenger_profile_trips_count)
    TextView profileTripsCount;

    @BindView(R.id.passenger_profile_gender)
    TextView profileGender;

    @BindView(R.id.passenger_profile_phone_number)
    TextView profilePhoneNumber;

    @BindView(R.id.passenger_profile_email)
    TextView profileEmail;

    @BindView(R.id.passenger_profile_email_icon)
    ImageButton email_icon;

    @BindView(R.id.passenger_profile_email_TV)
    TextView email_TV;

    @BindView(R.id.passenger_profile_full_name)
    TextView profileFullName;

    @BindView(R.id.passenger_profile_report_button)
    Button reportButton;

    public PassengerProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_passenger_profile, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromAPI();
        return view;
    }

    private void initViews(){
        backArrowButton.setOnClickListener(onBackArrowClicked);
        //reportButton.setOnClickListener(onReportClicked);
    }

    private void getDataFromAPI(){
//        UserViewModel.getINSTANCE().getPassengerProfile(GET_USER_ID());
//        UserViewModel.getINSTANCE().profileMutableLiveData.observe(requireActivity(), profileObserver);
    }

//    private final Observer<Pair<ProfileModel, String>> profileObserver = new Observer<Pair<ProfileModel, String>>() {
//        @Override
//        public void onChanged(Pair<ProfileModel, String> profileModelStringPair) {
//            if (profileModelStringPair != null) {
//                if (profileModelStringPair.first != null) {
//                    setData(profileModelStringPair.first.data);
//                    profileData = profileModelStringPair.first.data;
//                    insertRoomObject(profileData);
//                } else {
//                    getDataFromRoom();
//                    new ErrorDialog(requireContext(), profileModelStringPair.second).show();
//                }
//            } else {
//                getDataFromRoom();
//                new ErrorDialog(requireContext(), "Error Connection").show();
//            }
//        }
//    };

//    public void insertRoomObject(ProfileData profileData) {
//
//
//        ProfileDatabase.getINSTANCE(requireContext())
//                .iProfileModel().insertProfile(profileData)
//                .subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
//            @Override
//            public void onSubscribe(@androidx.annotation.NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//
//            @Override
//            public void onError(@androidx.annotation.NonNull Throwable e) {
//                Log.e(TAG, "onError: " + e.toString());
//            }
//        });
//    }

    private void getDataFromRoom(){
//        ProfileDatabase.getINSTANCE(requireContext())
//                .iProfileModel()
//                .getProfile(GET_USER_ID())
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<ProfileData>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(@NonNull ProfileData profileData) {
//                        setData(profileData);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//                });
    }

    private void setData(){
//        setName(data.first_name, data.last_name);
//        profileTripsCount.setText(String.valueOf(data.customer_trip_count));
//        profileFollowingCount.setText(String.valueOf(data.organizer_follow_count));
//        profileGender.setText(data.gender);
//        profilePhoneNumber.setText(data.phone_number);
//        setEmail(data.email);
//        downloadUserImage(data.photo);
    }

    private void downloadUserImage(String url){

        ImageViewer.downloadCircleImage(requireContext(),profileImage,R.drawable.profile_place_holder,IMAGE_BASE_URL+url);
    }


    private void setEmail(String email){
        if(email == null){
            profileEmail.setVisibility(View.GONE);
            email_icon.setVisibility(View.GONE);
            email_TV.setVisibility(View.GONE);
        } else
            profileEmail.setText(email);
    }

    @SuppressLint("SetTextI18n")
    private void setName(String firstName, String lastName){
        profileFullName.setText(firstName + " " + lastName);
    }

    private final View.OnClickListener onBackArrowClicked = view -> FN.popTopStack(requireActivity());

    //private final View.OnClickListener onReportClicked = v -> new ReportDialog(requireContext(), profileFullName.getText().toString()).show();
}