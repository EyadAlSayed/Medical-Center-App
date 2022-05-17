package com.example.dayout_organizer.ui.fragments.trips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class FilterFragment extends Fragment {

    View view;

    @BindView(R.id.filter_title)
    EditText filterTitle;

    @BindView(R.id.filter_min_price)
    EditText filterMinPrice;

    @BindView(R.id.filter_max_price)
    EditText filterMaxPrice;

    @BindView(R.id.filter_spinner)
    Spinner filterSpinner;

    @BindView(R.id.filter_button)
    Button filterButton;

    public static boolean isFilterOpen = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filter_fragment, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        filterButton.setOnClickListener(onFilterClicked);
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.trip_types));
        filterSpinner.setAdapter(typesAdapter);
    }

    private final View.OnClickListener onFilterClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: Filter - Caesar.
            FN.popStack(requireActivity());
            isFilterOpen = false;
        }
    };

}
