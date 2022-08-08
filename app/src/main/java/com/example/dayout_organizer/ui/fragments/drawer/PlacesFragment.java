package com.example.dayout_organizer.ui.fragments.drawer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.PlacesAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.place.PlaceData;
import com.example.dayout_organizer.models.place.PlacePaginationModel;
import com.example.dayout_organizer.room.placeRoom.databases.PlaceDataBase;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
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


public class PlacesFragment extends Fragment {

    View view;
    PlacesAdapter placesAdapter;
    LoadingDialog loadingDialog;

    @BindView(R.id.places_rc)
    RecyclerView placesRc;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.page_loading_pbar)
    ProgressBar pageLoadingBar;
    @BindView(R.id.back_arrow)
    ImageButton backArrow;

    int pageNumber;
    boolean canPaginate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_places, container, false);
        ButterKnife.bind(this, view);
        initView();
        getDataFromApi();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity) requireActivity()).hideBottomBar();
        ((MainActivity) requireActivity()).hideDrawerButton();
        super.onStart();
    }

    private void initView() {
        pageNumber = 1;
        loadingDialog = new LoadingDialog(requireContext());
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        backArrow.setOnClickListener(v -> FN.popTopStack(requireActivity()));
        initRc();
    }

    private void initRc() {
        placesRc.setHasFixedSize(true);
        placesRc.addOnScrollListener(onScroll);
        placesRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        placesAdapter = new PlacesAdapter(new ArrayList<>(), requireContext());
        placesRc.setAdapter(placesAdapter);
    }

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

    private void getDataFromApi() {
        loadingDialog.show();
        PlaceViewModel.getINSTANCE().getPlaces(pageNumber);
        PlaceViewModel.getINSTANCE().placeMutableLiveData.observe(requireActivity(), placesObserver);
    }

    private void getDataFromRoom() {
        PlaceDataBase.getINSTANCE(requireContext())
                .iPlaces()
                .getPlaces()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<PlaceData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<PlaceData> data) {
                        placesAdapter.refresh(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("getter  places roomDB", "onError: " + e.toString());
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


    private final Observer<Pair<PlacePaginationModel, String>> placesObserver = new Observer<Pair<PlacePaginationModel, String>>() {
        @Override
        public void onChanged(Pair<PlacePaginationModel, String> popularPlaceStringPair) {
            loadingDialog.dismiss();
            hideLoadingBar();
            if (popularPlaceStringPair != null) {
                if (popularPlaceStringPair.first != null) {
                    placesAdapter.refresh(popularPlaceStringPair.first.data.data);
                } else {
                    getDataFromRoom();
                    new ErrorDialog(requireContext(), popularPlaceStringPair.second).show();
                }
            } else {
                getDataFromRoom();
                new ErrorDialog(requireContext(), getResources().getString(R.string.error_connection)).show();
            }
        }
    };

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            swipeRefreshLayout.setEnabled(false);
            getDataFromApi();
        }
    };


}