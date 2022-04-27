package com.example.dayout_organizer.ui.fragments.drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.ui.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DrawerFragment extends Fragment {


    View view;
    @BindView(R.id.drawer_close_btn)
    ImageButton drawerCloseButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        drawerCloseButton.setOnClickListener(onCloseClicked);
        ((MainActivity)requireActivity()).showBottomBar();
    }

    private final View.OnClickListener onCloseClicked = v -> FN.popStack(requireActivity());
}