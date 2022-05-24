package com.example.dayout_organizer.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Pair;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.PickPlaceAdapter;
import com.example.dayout_organizer.models.place.Place;
import com.example.dayout_organizer.models.trip.PlaceTripData;
import com.example.dayout_organizer.models.trip.create.CreateTripPlace;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.viewModels.PlaceViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickPlaceDialog extends Dialog {

    Context context;
    PickPlaceAdapter pickPlaceAdapter;

    @BindView(R.id.pick_place_rc)
    RecyclerView pickPlaceRc;

    CreateTripPlace createTripPlace;

    int order_number,tripId;


    public PickPlaceDialog(@NonNull Context context,int tripId) {
        super(context);
        setContentView(R.layout.pick_place_dialog);
        
        ButterKnife.bind(this);
        this.context = context;
        this.tripId = tripId;
        initView();
    }

    private void initView() {
        order_number = 0;
        createTripPlace = new CreateTripPlace(tripId,new ArrayList<>());
        initRc();
    }

    private void initRc() {
        pickPlaceRc.setHasFixedSize(true);
        pickPlaceRc.setLayoutManager(new LinearLayoutManager(context));
        pickPlaceAdapter = new PickPlaceAdapter(new ArrayList<>(),context);
        pickPlaceAdapter.setOnItemClick(onItemClick);
        pickPlaceRc.setAdapter(pickPlaceAdapter);
    }

    public CreateTripPlace getCreateTripPlace(){
     return createTripPlace;
    }

    private final PickPlaceAdapter.OnItemClick onItemClick = new PickPlaceAdapter.OnItemClick() {
        @Override
        public void OnCreateTripPlaceItemClicked(int position, List<Place.Data> list) {
            createTripPlace.places.add(new PlaceTripData(
                    list.get(position).id,
                    list.get(position).name
                    ,++order_number
                    ,list.get(position).description
            ));

            cancel();
        }
    };

    private void getDataFromApi(){
        PlaceViewModel.getINSTANCE().placeMutableLiveData.observe((MainActivity) context, new Observer<Pair<Place, String>>() {
            @Override
            public void onChanged(Pair<Place, String> placeStringPair) {
                if (placeStringPair != null){
                    if (placeStringPair.first != null){
                        pickPlaceAdapter.refresh(placeStringPair.first.data.data);
                    }
                    else {
                        new ErrorDialog(context,placeStringPair.second).show();
                    }
                }
                else {
                    new ErrorDialog(context,"Error Connection").show();
                }
            }
        });
    }

    @Override
    public void show() {
        getDataFromApi();

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
