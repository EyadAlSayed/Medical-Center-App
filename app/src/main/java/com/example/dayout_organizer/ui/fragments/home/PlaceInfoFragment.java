package com.example.dayout_organizer.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.dayout_organizer.R;
import com.example.dayout_organizer.models.place.PopularPlaceData;
import com.example.dayout_organizer.models.place.PlacePhoto;
import com.example.dayout_organizer.ui.activities.MainActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.api.ApiClient.BASE_URL;


public class PlaceInfoFragment extends Fragment {


    View view;
    @BindView(R.id.image_slider)
    ImageSlider imageSlider;
    @BindView(R.id.place_full_info_txt)
    TextView placeFullInfoTxt;
    @BindView(R.id.short_descrption)
    TextView shortDescrption;

    PopularPlaceData popularPlaceData;

    public PlaceInfoFragment(PopularPlaceData popularPlaceData) {
        this.popularPlaceData = popularPlaceData;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_place_info, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity) requireActivity()).hideBottomBar();
        super.onStart();
    }


    private void initView() {
        initImageSlider(popularPlaceData.photos);
        placeFullInfoTxt.setText(popularPlaceData.description);
        shortDescrption.setText(popularPlaceData.summary);
    }

    private void initImageSlider(List<PlacePhoto> photos) {

//        List<SlideModel> slideModels = new ArrayList<>();
//        for (PlacePhoto ph : popularPlaceData.photos) {
//            slideModels.add(new SlideModel(ph. + ph.id
//                    , ScaleTypes.FIT));
//        }
//        imageSlider.setImageList(slideModels);
//        imageSlider.setScrollBarFadeDuration(10000);
        List<SlideModel> slideModels = new ArrayList<>();

        String baseUrl = BASE_URL.substring(0,BASE_URL.length()-1);

        for (PlacePhoto ph : photos) {
            slideModels.add(new SlideModel(baseUrl + ph.path
                    , ScaleTypes.FIT));
        }

        imageSlider.setImageList(slideModels);

        imageSlider.setScrollBarFadeDuration(10000);
    }
}