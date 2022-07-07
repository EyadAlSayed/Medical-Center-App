package com.example.dayout_organizer.ui.fragments.trips.myTrip.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dayout_organizer.R;

import com.example.dayout_organizer.adapter.recyclers.myTrips.UpComingTripAdapter;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripPaginationModel;
import com.example.dayout_organizer.room.tripRoom.databases.TripDataBases;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.ui.fragments.trips.myTrip.FilterFragment;
import com.example.dayout_organizer.ui.fragments.trips.myTrip.interfaces.IMyTrip;
import com.example.dayout_organizer.viewModels.TripViewModel;
import com.google.gson.JsonObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class UpComingTripFragment extends Fragment implements IMyTrip {


    View view;

    @BindView(R.id.up_coming_trip_rc)
    RecyclerView upComingTripRc;

    @BindView(R.id.upcoming_trips_no_trips)
    TextView upcomingTripsNoTrips;

    @BindView(R.id.upcoming_trips_refresh_layout)
    SwipeRefreshLayout upcomingTripsRefreshLayout;

    @BindView(R.id.upcoming_trips_loading_bar)
    ProgressBar pageLoadingBar;

    LoadingDialog loadingDialog;
    UpComingTripAdapter adapter;



    int pageNumber;
    boolean canPaginate;

    public UpComingTripFragment(UpComingTripAdapter adapter) {
        this.adapter = adapter;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_up_coming_trip, container, false);
        ButterKnife.bind(this, view);
        initView();
        getDataFromApi(new JsonObject());
        return view;
    }

    @Override
    public void onResume() {
        FilterFragment.iMyTrip = this;
        super.onResume();
    }

    private void initView() {
        pageNumber = 1;
        loadingDialog = new LoadingDialog(requireContext());
        initRc();
    }

    private void initRc() {
        upComingTripRc.setHasFixedSize(true);
        upComingTripRc.addOnScrollListener(onScroll);
        upComingTripRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        upComingTripRc.setAdapter(adapter);
    }


    private void setAsUpcoming(List<TripData> list) {
        for (TripData trip : list) {
            trip.isUpcoming = true;
        }
    }

    private void getDataFromApi(JsonObject requestBody) {
        loadingDialog.show();
        TripViewModel.getINSTANCE().getUpcomingTrips(requestBody, pageNumber);
        TripViewModel.getINSTANCE().upcomingTripsMutableLiveData.observe(requireActivity(), upcomingTripObserver);
    }

    private JsonObject getRequestBody(String place,String title,String type,String minPrice,String maxPrice){
        JsonObject object = new JsonObject();
        if (!place.equals(""))
            object.addProperty("place", place);
        if (!title.equals(""))
            object.addProperty("title", title);
        if (!type.equals(getString(R.string.any)))
            object.addProperty("type", type);
        if (!minPrice.equals(""))
            object.addProperty("min_price", Integer.parseInt(minPrice));
        if (!maxPrice.equals(""))
            object.addProperty("max_price", Integer.parseInt(maxPrice));
        return object;
    }

    private final Observer<Pair<TripPaginationModel, String>> upcomingTripObserver = new Observer<Pair<TripPaginationModel, String>>() {
        @Override
        public void onChanged(Pair<TripPaginationModel, String> listStringPair) {
            loadingDialog.dismiss();
            hideLoadingBar();
            if (listStringPair != null) {
                if (listStringPair.first != null) {
                    if (listStringPair.first.data.data.isEmpty()) {
                        upcomingTripsRefreshLayout.setVisibility(View.GONE);
                        upcomingTripsNoTrips.setVisibility(View.VISIBLE);
                    } else {
                        upcomingTripsRefreshLayout.setVisibility(View.VISIBLE);
                        upcomingTripsNoTrips.setVisibility(View.GONE);
                        setAsUpcoming(listStringPair.first.data.data);
                        adapter.addAndRefresh(listStringPair.first.data.data);
                    }
                    canPaginate = (listStringPair.first.data.next_page_url != null);
                } else {
                    getDataFromRoom();
                    new ErrorDialog(requireContext(), listStringPair.second).show();
                }
            } else {
                getDataFromRoom();
                new ErrorDialog(requireContext(), "Error Connection").show();
            }

        }
    };

    private void hideLoadingBar() {
        if (pageLoadingBar.getVisibility() == View.GONE) return;

        pageLoadingBar.animate().setDuration(400).alpha(0);
        new Handler(Looper.getMainLooper()).postDelayed(() -> pageLoadingBar.setVisibility(View.GONE), 450);
    }

    private void showLoadingBar() {
        if (pageLoadingBar.getVisibility() == View.VISIBLE) return;

        pageLoadingBar.setAlpha(1);
        pageLoadingBar.setVisibility(View.VISIBLE);
    }

    private void getDataFromRoom() {
        TripDataBases.getINSTANCE(requireContext())
                .iTrip()
                .getUpComingTripData()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<TripData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<TripData> tripData) {
                        adapter.refresh(tripData);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private final RecyclerView.OnScrollListener onScroll = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@androidx.annotation.NonNull RecyclerView recyclerView, int newState) {
            if (newState == 1 && canPaginate) {    // is scrolling
                pageNumber++;
                showLoadingBar();
                getDataFromApi(new JsonObject());
                canPaginate = false;
            }

            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@androidx.annotation.NonNull RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);
        }
    };

    @Override
    public void getTripInfo(String place, String title, String minPrice, String maxPrice, String tripType) {
        getDataFromApi(getRequestBody(place,title,tripType,minPrice,maxPrice));
    }
}