package com.example.dayout_organizer.ui.dialogs.pick;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.PickPlaceAdapter;
import com.example.dayout_organizer.models.place.PlaceData;
import com.example.dayout_organizer.models.place.PlacePaginationModel;
import com.example.dayout_organizer.models.trip.PlaceTripData;
import com.example.dayout_organizer.models.trip.create.CreateTripPlace;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.viewModels.PlaceViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class PickPlaceDialog extends Dialog {

    Context context;
    PickPlaceAdapter pickPlaceAdapter;


    @BindView(R.id.pick_place_rc)
    RecyclerView pickPlaceRc;

    @BindView(R.id.page_loading_pbar)
    ProgressBar pageLoadingBar;

    CreateTripPlace createTripPlace;

    int order_number, tripId;

    int pageIndex;
    boolean canPaginate;


    public PickPlaceDialog(@NonNull Context context, int tripId) {
        super(context);
        setContentView(R.layout.pick_place_dialog);

        ButterKnife.bind(this);
        this.context = context;
        this.tripId = tripId;
        this.pageIndex = 1;
        initView();

        getDataFromApi();
    }

    private void initView() {
        order_number = 0;
        createTripPlace = new CreateTripPlace(tripId, new ArrayList<>());
        initRc();
    }

    private void initRc() {
        pickPlaceRc.setHasFixedSize(true);
        pickPlaceRc.addOnScrollListener(onRcScrolled);
        pickPlaceRc.setLayoutManager(new LinearLayoutManager(context));
        pickPlaceAdapter = new PickPlaceAdapter(new ArrayList<>(), context);
        pickPlaceAdapter.setOnItemClick(onItemClick);
        pickPlaceRc.setAdapter(pickPlaceAdapter);
    }

    private void getDataFromApi() {
        PlaceViewModel.getINSTANCE().getPlaces(pageIndex);
        PlaceViewModel.getINSTANCE().placeMutableLiveData.observe((MainActivity) context, new Observer<Pair<PlacePaginationModel, String>>() {
            @Override
            public void onChanged(Pair<PlacePaginationModel, String> placeStringPair) {
                hideLoadingBar();
                if (placeStringPair != null) {
                    if (placeStringPair.first != null) {
                        pickPlaceAdapter.addAndRefresh(placeStringPair.first.data.data);
                        canPaginate = (placeStringPair.first.data.next_page_url != null);
                    } else {
                        new ErrorDialog(context, placeStringPair.second).show();
                    }
                } else {
                    new ErrorDialog(context, "Error Connection").show();
                }
            }
        });

    }

    public void setTripPlace(List<PlaceTripData> placeTripData) {
        createTripPlace.places.addAll(placeTripData);
    }

    public CreateTripPlace getCreateTripPlace() {
        return createTripPlace;
    }

    private final PickPlaceAdapter.OnItemClick onItemClick = new PickPlaceAdapter.OnItemClick() {
        @Override
        public void OnCreateTripPlaceItemClicked(int position, List<PlaceData> list) {
            createTripPlace.places.add(new PlaceTripData(
                    list.get(position).id,
                    list.get(position).name
                    , ++order_number
                    , list.get(position).description
            ));

            cancel();
        }
    };


    // pagination method

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

    private final RecyclerView.OnScrollListener onRcScrolled = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState == 1 && canPaginate) {    // is scrolling
                pageIndex++;
                showLoadingBar();
                getDataFromApi();
                canPaginate = false;
            }

            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);
        }
    };


    @Override
    public void show() {
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(wlp);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // match width dialog
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.show();
    }
}
