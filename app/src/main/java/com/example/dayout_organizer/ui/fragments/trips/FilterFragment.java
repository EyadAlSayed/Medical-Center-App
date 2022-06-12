package com.example.dayout_organizer.ui.fragments.trips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;

import com.example.dayout_organizer.adapter.recyclers.myTrips.ActiveTripAdapter;
import com.example.dayout_organizer.adapter.recyclers.myTrips.OldTripAdapter;
import com.example.dayout_organizer.adapter.recyclers.myTrips.UpComingTripAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.models.tripType.TripType;
import com.example.dayout_organizer.models.tripType.TripTypeModel;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.ArrayList;
import java.util.List;

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

    LoadingDialog loadingDialog;

    public static boolean isFilterOpen = false;

    int filterType;

    ActiveTripAdapter activeTripAdapter;
    UpComingTripAdapter upComingTripAdapter;
    OldTripAdapter oldTripAdapter;

    public FilterFragment(ActiveTripAdapter activeTripAdapter, int type) {
        this.activeTripAdapter = activeTripAdapter;
        this.filterType = type;
    }

    public FilterFragment(UpComingTripAdapter upComingTripAdapter, int type) {
        this.upComingTripAdapter = upComingTripAdapter;
        this.filterType = type;
    }

    public FilterFragment(OldTripAdapter oldTripAdapter, int type) {
        this.oldTripAdapter = oldTripAdapter;
        this.filterType = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filter_fragment, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromAPI();
        return view;
    }

    private void initViews() {
        loadingDialog = new LoadingDialog(requireContext());
        filterButton.setOnClickListener(onFilterClicked);
    }

    private void getDataFromAPI() {
        TripViewModel.getINSTANCE().getTripType();
        TripViewModel.getINSTANCE().tripTypeTripMutableLiveData.observe(requireActivity(), typeObserver);
    }

    private final Observer<Pair<TripTypeModel, String>> typeObserver = typeStringPair -> {
        if (typeStringPair != null) {
            if (typeStringPair.first != null) {
                initSpinner(getDataName(typeStringPair.first));
            }
        }
    };

    private void initSpinner(String[] spinnerItem) {
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                spinnerItem);

        filterSpinner.setAdapter(typesAdapter);
    }

    private String[] getDataName(TripTypeModel model) {
        List<String> names = new ArrayList<>();
        names.add("Any");
        for (TripType t : model.data) {
            names.add(t.name);
        }

        return names.toArray(new String[0]);
    }

    private final View.OnClickListener onFilterClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadingDialog.show();

            showFilteredTrips();
            FN.popStack(requireActivity());
            isFilterOpen = false;
        }
    };

    private void showFilteredTrips() {
        if (filterType == 1) {
            TripViewModel.getINSTANCE().getHistoryTrips();
            TripViewModel.getINSTANCE().historyTripsMutableLiveData.observe(requireActivity(), new Observer<Pair<TripModel, String>>() {
                @Override
                public void onChanged(Pair<TripModel, String> tripModelStringPair) {
                    loadingDialog.dismiss();
                    if (tripModelStringPair != null) {
                        if (tripModelStringPair.first != null) {
                            oldTripAdapter.refresh(filterList(tripModelStringPair.first.data));
                        } else
                            new ErrorDialog(requireContext(), tripModelStringPair.second).show();
                    } else
                        new ErrorDialog(requireContext(), "Error Connection").show();
                }
            });
        } else if (filterType == 2) {
            TripViewModel.getINSTANCE().getUpcomingTrips();
            TripViewModel.getINSTANCE().upcomingTripsMutableLiveData.observe(requireActivity(), new Observer<Pair<TripModel, String>>() {
                @Override
                public void onChanged(Pair<TripModel, String> tripModelStringPair) {
                    loadingDialog.dismiss();
                    if (tripModelStringPair != null) {
                        if (tripModelStringPair.first != null) {
                            upComingTripAdapter.refresh(filterList(tripModelStringPair.first.data));
                        } else
                            new ErrorDialog(requireContext(), tripModelStringPair.second).show();
                    } else
                        new ErrorDialog(requireContext(), "Error Connection").show();
                }
            });
        } else if (filterType == 3) {
            TripViewModel.getINSTANCE().getActiveTrips();
            TripViewModel.getINSTANCE().activeTripsMutableLiveData.observe(requireActivity(), new Observer<Pair<TripModel, String>>() {
                @Override
                public void onChanged(Pair<TripModel, String> tripModelStringPair) {
                    loadingDialog.dismiss();
                    if (tripModelStringPair != null) {
                        if (tripModelStringPair.first != null) {
                            activeTripAdapter.refresh(filterList(tripModelStringPair.first.data));
                        } else
                            new ErrorDialog(requireContext(), tripModelStringPair.second).show();
                    } else
                        new ErrorDialog(requireContext(), "Error Connection").show();
                }
            });
        }
    }

    private ArrayList<TripData> filterList(ArrayList<TripData> list) {

        list = filterListOnTitle(list);
        list = filterListOnMinPrice(list);
        list = filterListOnMaxPrice(list);
        list = filterListOnType(list);

        return list;
    }

    private ArrayList<TripData> filterListOnTitle(ArrayList<TripData> list) {
        if (!filterTitle.getText().toString().equals("")) {

            ArrayList<TripData> filteredTrips = new ArrayList<>();

            for (TripData trip : list) {
                if (trip.title.contains(filterTitle.getText().toString())) {
                    filteredTrips.add(trip);
                }
            }
            return filteredTrips;
        } else
            return list;
    }

    private ArrayList<TripData> filterListOnMinPrice(ArrayList<TripData> list) {
        if (!filterMinPrice.getText().toString().equals("")) {
            ArrayList<TripData> filteredTrips = new ArrayList<>();

            if (Integer.parseInt(filterMinPrice.getText().toString()) > 0) {
                for (TripData trip : list) {
                    if (trip.price >= Integer.parseInt(filterMinPrice.getText().toString())) {
                        filteredTrips.add(trip);
                    }
                }
            }
            return filteredTrips;
        } else
            return list;
    }

    private ArrayList<TripData> filterListOnMaxPrice(ArrayList<TripData> list) {
        if (!filterMaxPrice.getText().toString().equals("")) {
            ArrayList<TripData> filteredTrips = new ArrayList<>();

            if (Integer.parseInt(filterMaxPrice.getText().toString()) > 0) {
                for (TripData trip : list) {
                    if (trip.price <= Integer.parseInt(filterMaxPrice.getText().toString())) {
                        filteredTrips.add(trip);
                    }
                }
            }
            return filteredTrips;
        } else
            return list;
    }

    private ArrayList<TripData> filterListOnType(ArrayList<TripData> list) {
        if (!filterSpinner.getSelectedItem().toString().equals("Any")) {
            ArrayList<TripData> filteredTrips = new ArrayList<>();

            for (TripData trip : list) {
                for (TripType tripType : trip.types) {
                    if (tripType.name.equals(filterSpinner.getSelectedItem().toString())) {
                        filteredTrips.add(trip);
                    }
                }
            }
            return filteredTrips;
        } else
            return list;
    }
}
