package com.example.dayout_organizer.ui.fragments.home;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.dayout_organizer.R;
import com.example.dayout_organizer.models.place.PlaceData;
import com.example.dayout_organizer.models.place.PlacePhoto;
import com.example.dayout_organizer.models.place.PlaceDetailsModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.viewModels.PlaceViewModel;


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

    PlaceData placeData;


//    public PlaceInfoFragment(PopularPlaceData popularPlaceData) {
//        this.popularPlaceData = popularPlaceData;
//    }

    int placeId;
    public PlaceInfoFragment(int placeId){
        this.placeId = placeId;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_place_info, container, false);
        ButterKnife.bind(this, view);

        getDataFromApi();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity) requireActivity()).hideBottomBar();
        super.onStart();
    }


    private void initInfo(PlaceData placeData) {
        initImageSlider(placeData);
        placeFullInfoTxt.setText(placeData.description);
        shortDescrption.setText(placeData.summary);
    }

    private void initImageSlider(PlaceData placeData) {
        List<SlideModel> slideModels = new ArrayList<>();
        String baseUrl = BASE_URL.substring(0, BASE_URL.length() - 1);
        for (PlacePhoto ph : placeData.photos) {
            slideModels.add(new SlideModel(baseUrl + ph.path
                    , ScaleTypes.FIT));
        }
        imageSlider.setImageList(slideModels);
        imageSlider.setScrollBarFadeDuration(10000);
    }

    private void getDataFromApi(){
        PlaceViewModel.getINSTANCE().getPlaceDetails(placeId);
        PlaceViewModel.getINSTANCE().placeDetailsMutableLiveData.observe(requireActivity(),placeDetailsModel);
    }
    private final Observer<Pair<PlaceDetailsModel,String>> placeDetailsModel = new Observer<Pair<PlaceDetailsModel, String>>() {
        @Override
        public void onChanged(Pair<PlaceDetailsModel, String> placeDetailsModelStringPair) {
            if (placeDetailsModelStringPair != null){
                if (placeDetailsModelStringPair.first != null){
                    initInfo(placeDetailsModelStringPair.first.data);
                }
                else {
                    new ErrorDialog(requireContext(),"Error Connection").show();
                }
            }
            else {
                new ErrorDialog(requireContext(),"Error Connection").show();
            }
        }
    };


}