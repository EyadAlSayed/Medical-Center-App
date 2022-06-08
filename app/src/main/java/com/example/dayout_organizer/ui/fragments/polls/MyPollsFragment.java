package com.example.dayout_organizer.ui.fragments.polls;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.PollsAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.poll.PollData;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.viewModels.PollViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class MyPollsFragment extends Fragment {

    View view;

    @BindView(R.id.polls_back_button)
    ImageButton pollsBackButton;

    @BindView(R.id.polls_recycler)
    RecyclerView pollsRecycler;

    @BindView(R.id.polls_add_button)
    FloatingActionButton pollsAddButton;

    PollsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_polls_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromAPI();

        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity)requireActivity()).hideBottomBar();
        super.onStart();
    }

    private void initViews() {
        initRecycler();
        pollsBackButton.setOnClickListener(onBackClicked);
        pollsAddButton.setOnClickListener(onAddClicked);
    }

    private void initRecycler(){
        pollsRecycler.setHasFixedSize(true);
        pollsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PollsAdapter(new ArrayList<>(), requireContext());
        pollsRecycler.setAdapter(adapter);
    }

    private void getDataFromAPI(){
//        List<PollData> polls = new ArrayList<>();
//
//        PollData poll1 = new PollData("Poll 1 Description");
//        PollData poll2 = new PollData("Poll 2 Description");
//        PollData poll3 = new PollData("Poll 3 Description");
//        PollData poll4 = new PollData("Poll 4 Description");
//        PollData poll5 = new PollData("Poll 5 Description");
//        PollData poll6 = new PollData("Poll 6 Description");
//
//        polls.add(poll1);
//        polls.add(poll2);
//        polls.add(poll3);
//        polls.add(poll4);
//        polls.add(poll5);
//        polls.add(poll6);
//
//        adapter.refreshList(polls);

        PollViewModel.getINSTANCE().getPolls();
        PollViewModel.getINSTANCE().pollsMutableLiveData.observe(requireActivity(), pollsObserver);
    }

    private final Observer<Pair<PollData, String>> pollsObserver = new Observer<Pair<PollData, String>>() {
        @Override
        public void onChanged(Pair<PollData, String> pollDataStringPair) {
            if(pollDataStringPair != null){
                if(pollDataStringPair.first != null){
                    //adapter.refreshList(pollDataStringPair.first);
                } else
                    new ErrorDialog(requireContext(), pollDataStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

    private final View.OnClickListener onBackClicked = v -> {FN.popStack(requireActivity());};

    private final View.OnClickListener onAddClicked = v -> {/*go to create poll fragment*/};
}
