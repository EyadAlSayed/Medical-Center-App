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
import com.example.dayout_organizer.adapter.recyclers.PickTypeAdapter;
import com.example.dayout_organizer.models.trip.TripType;
import com.example.dayout_organizer.models.trip.Type;
import com.example.dayout_organizer.models.trip.create.CreateTripType;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickTripTypeDialog extends Dialog {

    Context context;
    PickTypeAdapter pickTypeAdapter;
    @BindView(R.id.pick_type_rc)
    RecyclerView pickTypeRc;

    int tripId;
    CreateTripType createTripType;

    public PickTripTypeDialog(@NonNull Context context,int tripId) {
        super(context);
        setContentView(R.layout.pick_type_dialog);
        setCancelable(false);
        ButterKnife.bind(this);
        this.context = context;
        this.tripId = tripId;
        initView();
    }

    private void initView() {
        createTripType  = new CreateTripType(tripId,new ArrayList<>());
        initRc();
    }

    private void initRc() {
        pickTypeRc.setHasFixedSize(true);
        pickTypeRc.setLayoutManager(new LinearLayoutManager(context));
        pickTypeAdapter = new PickTypeAdapter(new ArrayList<>(),context);
        pickTypeAdapter.setOnItemClick(onItemClick);
        pickTypeRc.setAdapter(pickTypeAdapter);
    }

    public CreateTripType getCreateTripType (){
        return  createTripType;
    }

    private final PickTypeAdapter.OnItemClick onItemClick = new PickTypeAdapter.OnItemClick() {
        @Override
        public void OnCreateTripPlaceItemClicked(int position, List<Type.Data> list) {
            createTripType.types.add(new CreateTripType.Type(list.get(position).id));
            cancel();
        }
    };
    private void getDataFromApi(){
        TripViewModel.getINSTANCE().tripTypeTripMutableLiveData.observe((MainActivity) context, new Observer<Pair<Type, String>>() {
            @Override
            public void onChanged(Pair<Type, String> listStringPair) {
                if (listStringPair != null){
                    if (listStringPair.first != null){
                        pickTypeAdapter.refresh(listStringPair.first.data);
                    }
                    else {
                        new ErrorDialog(context,listStringPair.second).show();
                    }
                }
                else {
                    new ErrorDialog(context,"Connection Error").show();
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
