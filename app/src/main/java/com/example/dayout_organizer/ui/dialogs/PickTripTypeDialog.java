package com.example.dayout_organizer.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.PickPlaceAdapter;
import com.example.dayout_organizer.adapter.recyclers.PickTypeAdapter;
import com.example.dayout_organizer.models.trip.create.CreateTripType;

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
        setContentView(R.layout.pick_place_dialog);
        setCancelable(false);
        ButterKnife.bind(this);
        this.context = context;
        this.tripId = tripId;
        initView();
    }

    private void initView() {
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
        public void OnCreateTripPlaceItemClicked(int position, List<String> list) {



            cancel();
        }
    };
    private void getDataFromApi(){

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
