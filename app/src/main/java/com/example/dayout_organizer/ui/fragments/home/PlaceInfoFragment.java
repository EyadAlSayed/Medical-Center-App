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
import com.example.dayout_organizer.models.PopularPlace;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaceInfoFragment extends Fragment {


    View view;
    @BindView(R.id.image_slider)
    ImageSlider imageSlider;
    @BindView(R.id.place_full_info_txt)
    TextView placeFullInfoTxt;

    PopularPlace.Data popularPlaceData;

    public PlaceInfoFragment(PopularPlace.Data popularPlaceData) {
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

    private void initView(){
        initImageSlider();
        placeFullInfoTxt.setText(popularPlaceData.description);
    }

    private void initImageSlider() {
        // TODO EYAD - add images url to slider

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.a, ScaleTypes.FIT)); // for one image
        slideModels.add(new SlideModel(R.drawable.aa, ScaleTypes.FIT)); // you can with title
        imageSlider.setImageList(slideModels);
        imageSlider.setScrollBarFadeDuration(10000);
    }
}