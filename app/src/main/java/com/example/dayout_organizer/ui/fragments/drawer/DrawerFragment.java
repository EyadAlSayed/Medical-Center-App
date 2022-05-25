package com.example.dayout_organizer.ui.fragments.drawer;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.ImageViewer;
import com.example.dayout_organizer.models.profile.ProfileData;
import com.example.dayout_organizer.models.profile.ProfileModel;
import com.example.dayout_organizer.models.profile.ProfileUser;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.LogOutDialog;
import com.example.dayout_organizer.ui.fragments.trips.myTrip.MyTripsFragment;
import com.example.dayout_organizer.viewModels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

import static com.example.dayout_organizer.api.ApiClient.BASE_URL;
import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;
import static com.example.dayout_organizer.config.AppSharedPreferences.GET_USER_ID;

@SuppressLint("NonConstantResourceId")
public class DrawerFragment extends Fragment {


    View view;

    @BindView(R.id.drawer_close_btn)
    ImageButton drawerCloseButton;
    @BindView(R.id.my_trip_txt)
    TextView myTripTxt;
    @BindView(R.id.polls_txt)
    TextView pollsTxt;
    @BindView(R.id.places_txt)
    TextView placesTxt;
    @BindView(R.id.notification_txt)
    TextView notificationTxt;
    @BindView(R.id.connect_us_txt)
    TextView connectUsTxt;
    @BindView(R.id.setting_txt)
    TextView settingTxt;
    @BindView(R.id.drawer_layout)
    ConstraintLayout drawerLayout;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.blur_view)
    BlurView blurView;
    @BindView(R.id.logout_txt)
    TextView logoutTxt;
    @BindView(R.id.drawer_userphoto)
    ImageView drawerUserphoto;
    @BindView(R.id.drawer_username)
    TextView drawerUsername;

    LogOutDialog logOutDialog;

    public static boolean isDrawerOpen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.bind(this, view);
        initView();
        getDataFromAPI();
        return view;
    }


    @Override
    public void onStart() {
        ((MainActivity) requireActivity()).hideDrawerButton();
        ((MainActivity) requireActivity()).hideBottomBar();
        isDrawerOpen = true;
        new Handler(Looper.getMainLooper()).postDelayed(this::initBlur, 1000);
        super.onStart();
    }

    @Override
    public void onStop() {
        ((MainActivity) requireActivity()).showDrawerButton();
        ((MainActivity) requireActivity()).showBottomBar();
        isDrawerOpen = false;
        super.onStop();
    }

    private void initView() {
        logOutDialog = new LogOutDialog(requireContext());
        drawerCloseButton.setOnClickListener(onCloseClicked);
        myTripTxt.setOnClickListener(onMyTripsClicked);
        settingTxt.setOnClickListener(onSettingClicked);
        notificationTxt.setOnClickListener(onNotificationsClicked);
        logoutTxt.setOnClickListener(onLogOutClicked);
    }

    private void getDataFromAPI() {
        UserViewModel.getINSTANCE().getOrganizerProfile(GET_USER_ID());
        UserViewModel.getINSTANCE().profileMutableLiveData.observe(requireActivity(), profileObserver);
    }

    private final Observer<Pair<ProfileModel, String>> profileObserver = new Observer<Pair<ProfileModel, String>>() {
        @Override
        public void onChanged(Pair<ProfileModel, String> profileModelStringPair) {
            if (profileModelStringPair != null) {
                if (profileModelStringPair.first != null) {
                    setData(profileModelStringPair.first.data.user);
                } else {
                    //getDataFromRoom();
                }
            } else {
                //getDataFromRoom();
            }
        }
    };

    private void setData(ProfileUser data) {
        drawerUsername.setText(data.first_name);
        downloadUserImage(data.photo);
    }

    private void downloadUserImage(String url){
        String baseUrl = BASE_URL.substring(0,BASE_URL.length()-1);
        if (baseUrl!= null)
        ImageViewer.downloadCircleImage(requireContext(),drawerUserphoto ,R.drawable.profile_place_holder,baseUrl+url);
    }


    private void initBlur() {
        float radius = 20f;

        View decorView = requireActivity().getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(requireContext()))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true);

    }

    private final View.OnClickListener onCloseClicked = v -> {
        blurView.setupWith(new ViewGroup(requireContext()) {
            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                // this for removing blur view group before deAttach the fragment
            }
        });
        new Handler(Looper.getMainLooper()).postDelayed(() -> FN.popTopStack(requireActivity()), 200);
    };

    private final View.OnClickListener onMyTripsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FN.popTopStack(requireActivity());
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new MyTripsFragment());
        }
    };

    private final View.OnClickListener onNotificationsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new NotificationFragment());
        }
    };

    private final View.OnClickListener onSettingClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new SettingsFragment(0));
        }
    };

    private final View.OnClickListener onLogOutClicked = v -> {
        new LogOutDialog(requireContext()).show();
    };


}