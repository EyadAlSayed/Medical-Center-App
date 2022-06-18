package com.example.dayout_organizer.ui.fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.HomePlaceAdapter;

import com.example.dayout_organizer.models.place.PlaceData;
import com.example.dayout_organizer.models.place.PlaceModel;
import com.example.dayout_organizer.room.popularPlaceRoom.databases.PopularPlaceDataBase;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.viewModels.PlaceViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends Fragment {


    View view;
    @BindView(R.id.home_place_rc)
    RecyclerView homePlaceRc;

    HomePlaceAdapter homePlaceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        getDataFromApi();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity)requireActivity()).showBottomBar();
        super.onStart();
    }

    private void initView() {
        initRc();
    }

    private void initRc(){
        homePlaceRc.setHasFixedSize(true);
        homePlaceRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        homePlaceAdapter = new HomePlaceAdapter(new ArrayList<>(),requireContext());
        homePlaceRc.setAdapter(homePlaceAdapter);
    }

    private void getDataFromApi(){
        PlaceViewModel.getINSTANCE().getPopularPlace();
        PlaceViewModel.getINSTANCE().popularPlaceMutableLiveData.observe(requireActivity(),popularPlaceObserver);
    }

    private void getDataFromRoom() {
        PopularPlaceDataBase.getINSTANCE(requireContext())
                .iPopularPlaces()
                .getPopularPlace()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<PlaceData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<PlaceData> data) {
                        homePlaceAdapter.refreshList(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("getter popular place roomDB", "onError: " + e.toString());
                    }
                });
    }

    private final Observer<Pair<PlaceModel,String>> popularPlaceObserver =  new Observer<Pair<PlaceModel, String>>() {
        @Override
        public void onChanged(Pair<PlaceModel, String> popularPlaceStringPair) {
            if (popularPlaceStringPair != null){
                if (popularPlaceStringPair.first != null){
                    homePlaceAdapter.refreshList(popularPlaceStringPair.first.data);
                }
                else {
                    getDataFromRoom();
                    new ErrorDialog(requireContext(),popularPlaceStringPair.second).show();
                }
            }
            else {
                getDataFromRoom();
                new ErrorDialog(requireContext(),"connection error").show();
            }
        }
    };
}