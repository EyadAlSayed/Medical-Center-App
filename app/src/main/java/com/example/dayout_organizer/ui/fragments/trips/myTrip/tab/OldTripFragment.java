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

import com.example.dayout_organizer.adapter.recyclers.myTrips.OldTripAdapter;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripPaginationModel;
import com.example.dayout_organizer.room.tripRoom.databases.TripDataBases;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.ui.fragments.trips.myTrip.FilterFragment;
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
public class OldTripFragment extends Fragment {


    View view;

    @BindView(R.id.old_trip_rc)
    RecyclerView oldTripRc;

    @BindView(R.id.old_trips_no_history)
    TextView oldTripsNoHistory;

    @BindView(R.id.old_trips_refresh_layout)
    SwipeRefreshLayout oldTripsRefreshLayout;

    @BindView(R.id.old_trips_loading_bar)
    ProgressBar pageLoadingBar;

    LoadingDialog loadingDialog;
    OldTripAdapter adapter;

    JsonObject requestBody;

    int pageNumber;
    boolean canPaginate;

    public OldTripFragment(OldTripAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_old_trip, container, false);
        ButterKnife.bind(this, view);
        initView();
        getDataFromApi();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("resuming");
        pageNumber = 1;
        requestBody = getRequestBody();
        getDataFromApi();
    }

    private void initView() {
        pageNumber = 1;
        loadingDialog = new LoadingDialog(requireContext());
        initRc();
        requestBody = getRequestBody();
    }

    private void initRc() {
        oldTripRc.setHasFixedSize(true);
        oldTripRc.addOnScrollListener(onScroll);
        oldTripRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        oldTripRc.setAdapter(adapter);
    }

    private JsonObject getRequestBody(){
        JsonObject object = new JsonObject();
        if (!FilterFragment.place.equals(""))
            object.addProperty("place", FilterFragment.place);
        if (!FilterFragment.title.equals(""))
            object.addProperty("title", FilterFragment.title);
        if (!FilterFragment.type.equals("Any"))
            object.addProperty("type", FilterFragment.type);
        if (!FilterFragment.minPrice.equals(""))
            object.addProperty("min_price", Integer.parseInt(FilterFragment.minPrice));
        if (!FilterFragment.maxPrice.equals(""))
            object.addProperty("max_price", Integer.parseInt(FilterFragment.maxPrice));
        return object;
    }

    private void getDataFromApi() {
        loadingDialog.show();
        TripViewModel.getINSTANCE().getHistoryTrips(requestBody, pageNumber);
        TripViewModel.getINSTANCE().historyTripsMutableLiveData.observe(requireActivity(), historyTripsObserver);
    }

    private final Observer<Pair<TripPaginationModel, String>> historyTripsObserver = new Observer<Pair<TripPaginationModel, String>>() {
        @Override
        public void onChanged(Pair<TripPaginationModel, String> tripModelStringPair) {
            loadingDialog.dismiss();
            hideLoadingBar();
            if (tripModelStringPair != null) {
                if (tripModelStringPair.first != null) {
                    if (tripModelStringPair.first.data.data.isEmpty()) {
                        oldTripsRefreshLayout.setVisibility(View.GONE);
                        oldTripsNoHistory.setVisibility(View.VISIBLE);
                    } else {
                        oldTripsRefreshLayout.setVisibility(View.VISIBLE);
                        oldTripsNoHistory.setVisibility(View.GONE);
                        adapter.addAndRefresh(tripModelStringPair.first.data.data);
                    }
                    canPaginate = (tripModelStringPair.first.data.next_page_url != null);
                } else {
                    getDataFromRoom();
                    new ErrorDialog(requireContext(), tripModelStringPair.second).show();
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
                .getHistoryTripData()
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
                getDataFromApi();
                canPaginate = false;
            }

            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@androidx.annotation.NonNull RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);
        }
    };
}