package com.example.dayout_organizer.ui.fragments.trips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.PassengersListAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.PassengerData;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.WarningDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class PassengersListFragment extends Fragment {

    View view;

    @BindView(R.id.passengers_list_back_button)
    ImageButton passengersListBackButton;

    @BindView(R.id.passengers_list_recycler_view)
    RecyclerView passengersListRecyclerView;

    @BindView(R.id.passengers_list_total)
    TextView passengersListTotal;

    PassengersListAdapter adapter;

    ArrayList<PassengerData> list = new ArrayList<>();

    private boolean isUpcoming;

    public PassengersListFragment(boolean isUpcoming) {
        this.isUpcoming = isUpcoming;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_passengers_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        //getDataFromAPI();
        PassengerData p1 = new PassengerData("SheeZ Farah", 2, false);
        PassengerData p2 = new PassengerData("Ab Ayham", 0, true);
        PassengerData p3 = new PassengerData("Sheikh Scientist", 8, true);
        PassengerData p4 = new PassengerData("Abd Cute", 1, false);
        PassengerData p11 = new PassengerData("SheeZ Farah", 2, false);
        PassengerData p22 = new PassengerData("Ab Ayham", 0, true);
        PassengerData p33 = new PassengerData("Sheikh Scientist", 8, true);
        PassengerData p44 = new PassengerData("Abd Cute", 1, false);
        PassengerData p111 = new PassengerData("SheeZ Farah", 2, false);
        PassengerData p222 = new PassengerData("Ab Ayham", 0, true);
        PassengerData p333 = new PassengerData("Sheikh Scientist", 8, true);
        PassengerData p444 = new PassengerData("Abd Cute", 1, false);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p11);
        list.add(p22);
        list.add(p33);
        list.add(p44);
        list.add(p111);
        list.add(p222);
        list.add(p333);
        list.add(p444);

        adapter.refreshList(list);

        return view;
    }

    private void initViews() {
        initRecycler();
        passengersListBackButton.setOnClickListener(onBackClicked);
    }

    private void initRecycler() {
        passengersListRecyclerView.setHasFixedSize(true);
        passengersListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PassengersListAdapter(new ArrayList<>(), requireContext(), isUpcoming);
        if (isUpcoming)
            new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(passengersListRecyclerView);
        passengersListRecyclerView.setAdapter(adapter);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            list.remove(viewHolder.getAdapterPosition());
//            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            new WarningDialog(requireContext(), "Are you sure you want to cancel this passenger's booking?", adapter, list, viewHolder.getAdapterPosition()).show();
        }
    };

    private final View.OnClickListener onBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.popStack(requireActivity());
        }
    };
}
