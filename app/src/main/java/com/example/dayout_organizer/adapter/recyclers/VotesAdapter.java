package com.example.dayout_organizer.adapter.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.models.poll.Choice;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VotesAdapter extends RecyclerView.Adapter<VotesAdapter.ViewHolder> {

    List<Choice> votes;
    Context context;
    int totalVotes;

    public VotesAdapter(List<Choice> votes, Context context){
        this.votes = votes;
        this.context = context;
    }

    public void refreshList(List<Choice> votes){
        this.votes = votes;
        this.totalVotes = votes.size();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_vote, parent, false);
        return new VotesAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VotesAdapter.ViewHolder holder, int position) {
        int percentage = calculatePercentage(votes.get(position).users.size());
        holder.voteTitle.setText(votes.get(position).value);
        holder.votePercentage.setText(percentage + "%");
        holder.progressBar.setProgress(percentage,true);
    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    private int calculatePercentage(int votes){
        return (votes * 100)/ totalVotes ;
    }

    @SuppressLint("NonConstantResourceId")
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vote_title)
        TextView voteTitle;

        @BindView(R.id.vote_percentage)
        TextView votePercentage;

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
