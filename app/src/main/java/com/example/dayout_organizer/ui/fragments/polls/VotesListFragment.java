package com.example.dayout_organizer.ui.fragments.polls;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.VotesAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.poll.Choice;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.viewModels.PollViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class VotesListFragment extends Fragment {

    View view;

    @BindView(R.id.votes_back_button)
    ImageButton votesBackButton;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.total_votes_TV)
    TextView totalVotesTV;

    VotesAdapter adapter;

    List<Choice> choices;

    public VotesListFragment(List<Choice> choices){
        this.choices = choices;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_poll_votes_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        setData();
        return view;
    }

    private void initViews() {
        initRecycler();
        votesBackButton.setOnClickListener(onBackClicked);
    }

    private void initRecycler(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new VotesAdapter(new ArrayList<>(), requireContext());
        recyclerView.setAdapter(adapter);
    }

    private void setData(){
        int totalVotes = getTotalVotes();
        totalVotesTV.setText(String.valueOf(totalVotes));
        adapter.refreshList(choices, totalVotes);
    }

    private int getTotalVotes(){
        int totalVotes = 0;
        for(Choice choice : choices){
            totalVotes += choice.users.size();
        }
        return totalVotes;
    }

    private final View.OnClickListener onBackClicked = v -> FN.popStack(requireActivity());
}