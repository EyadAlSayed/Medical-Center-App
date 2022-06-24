package com.example.dayout_organizer.ui.fragments.trips.details;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.RoadMapAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.roadMap.RoadMapData;
import com.example.dayout_organizer.models.roadMap.RoadMapModel;
import com.example.dayout_organizer.models.trip.PlaceTripData;
import com.example.dayout_organizer.room.roadMapRoom.databases.RoadMapDatabase;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RoadMapFragment extends Fragment {


    View view;
    @BindView(R.id.back_arrow)
    ImageButton backArrow;
    @BindView(R.id.road_map_rc)
    RecyclerView roadMapRc;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    RoadMapAdapter roadMapAdapter;
    int tripId;
    RoadMapFragment(int tripId){
        this.tripId = tripId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_road_map, container, false);
        ButterKnife.bind(this, view);
        initView();
        getDataFromApi();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity)requireActivity()).hideDrawerButton();
        super.onStart();
    }

    private void initView() {
        backArrow.setOnClickListener(v -> FN.popTopStack(requireActivity()));
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        initRc();

    }

    private void initRc(){
        roadMapRc.setHasFixedSize(true);
        roadMapRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        roadMapAdapter = new RoadMapAdapter(new ArrayList<>(),requireContext());
        roadMapRc.setAdapter(roadMapAdapter);
    }

    private void getDataFromApi() {
        TripViewModel.getINSTANCE().getRoadMap(tripId);
        TripViewModel.getINSTANCE().roadMapMutableLiveData.observe(requireActivity(),roadMapObserver);
    }
    private final Observer<Pair<RoadMapModel,String>> roadMapObserver = new Observer<Pair<RoadMapModel, String>>() {
        @Override
        public void onChanged(Pair<RoadMapModel, String> roadMapModelStringPair) {
            if (roadMapModelStringPair != null){
                if (roadMapModelStringPair.first != null){
                    roadMapAdapter.refresh(roadMapModelStringPair.first.data.place_trips);
                    insertRoomObject(roadMapModelStringPair.first.data);
                }
                else {
                    getDataFromRoom();
                    new ErrorDialog(requireContext(),roadMapModelStringPair.second).show();
                }
            }else {
                getDataFromRoom();
                new ErrorDialog(requireContext(),"Error Connection").show();
            }
            swipeRefreshLayout.setEnabled(true);
            swipeRefreshLayout.setRefreshing(false);
        }
    };


    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            swipeRefreshLayout.setEnabled(false);
            getDataFromApi();
        }
    };

    public void insertRoomObject(RoadMapData roadMapData) {

        // insert object in room database
        ((MainActivity) requireActivity()).iRoadMap
                .insertRoadMap(roadMapData)
                .subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@androidx.annotation.NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@androidx.annotation.NonNull Throwable e) {

            }
        });
    }

    private void getDataFromRoom() {
        RoadMapDatabase.getINSTANCE(requireContext())
                .iRoadMap()
                .getRoadMap()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RoadMapData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RoadMapData roadMapData) {
                        roadMapAdapter.refresh(roadMapData.place_trips);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}