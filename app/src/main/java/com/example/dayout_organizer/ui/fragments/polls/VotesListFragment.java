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
import com.example.dayout_organizer.models.poll.VoteData;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_poll_votes_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromAPI();

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

    private void getDataFromAPI(){
//        List<VoteData> votes = new ArrayList<>();
//
//        VoteData vote1 = new VoteData("Vote1", 50);
//        VoteData vote2 = new VoteData("Vote2", 100);
//        VoteData vote3 = new VoteData("Vote3", 20);
//        VoteData vote4 = new VoteData("Vote4", 20);
//        VoteData vote5 = new VoteData("Vote5", 10);
//
//        votes.add(vote1);
//        votes.add(vote2);
//        votes.add(vote3);
//        votes.add(vote4);
//        votes.add(vote5);
//
//        adapter.refreshList(votes, 200);
//
//        totalVotesTV.setText(String.valueOf(200));
        PollViewModel.getINSTANCE().getVotes();
        PollViewModel.getINSTANCE().votesMutableLiveData.observe(requireActivity(), votesObserver);
    }

    private final Observer<Pair<VoteData, String>> votesObserver = new Observer<Pair<VoteData, String>>() {
        @Override
        public void onChanged(Pair<VoteData, String> voteDataStringPair) {
            if(voteDataStringPair != null){
                if(voteDataStringPair.first != null){
                    //totalVotesTV.setText(voteDataStringPair.first.size());
                    //adapter.refreshList(voteDataStringPair.first);
                } else
                    new ErrorDialog(requireContext(), voteDataStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

    private final View.OnClickListener onBackClicked = v -> FN.popStack(requireActivity());
}