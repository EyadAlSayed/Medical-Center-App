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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickPlaceDialog extends Dialog {

    Context context;
    PickPlaceAdapter pickPlaceAdapter;
    @BindView(R.id.pick_place_rc)
    RecyclerView pickPlaceRc;

    public PickPlaceDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.pick_place_dialog);
        setCancelable(false);
        ButterKnife.bind(this);
        this.context = context;
        initView();
    }

    private void initView() {
        initRc();
    }

    private void initRc() {
        pickPlaceRc.setHasFixedSize(true);
        pickPlaceRc.setLayoutManager(new LinearLayoutManager(context));
        pickPlaceAdapter = new PickPlaceAdapter(new ArrayList<>(),context);
        pickPlaceAdapter.setOnItemClick(onItemClick);
        pickPlaceRc.setAdapter(pickPlaceAdapter);
    }

    private final PickPlaceAdapter.OnItemClick onItemClick = new PickPlaceAdapter.OnItemClick() {
        @Override
        public void OnCreateTripPlaceItemClicked(int position, List<String> list) {



            cancel();
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
