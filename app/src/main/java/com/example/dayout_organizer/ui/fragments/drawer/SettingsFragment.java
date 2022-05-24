package com.example.dayout_organizer.ui.fragments.drawer;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.fragment.app.Fragment;


import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.ui.activities.MainActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.api.ApiClient.BASE_URL;


public class SettingsFragment extends Fragment {


    View view;
    @BindView(R.id.back_arrow)
    ImageButton backArrow;
    @BindView(R.id.base_url)
    EditText baseUrl;
    @BindView(R.id.confirm_button)
    Button confirmButton;
    @BindView(R.id.language_switch)
    Switch languageSwitch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private  void initView(){
        baseUrl.setText(BASE_URL);
        confirmButton.setOnClickListener(onConfirmClicked);
        backArrow.setOnClickListener(v -> FN.popTopStack(requireActivity()));
        languageSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ((MainActivity)requireActivity()).changeLanguage("en",true);
            } else {
                ((MainActivity)requireActivity()).changeLanguage("ar",true);
            }
        });
    }






    private final View.OnClickListener onConfirmClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BASE_URL = baseUrl.getText().toString();
            FN.popTopStack(requireActivity());
        }
    };


}