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

    List<Choice> choices;
    Context context;
    int totalVotes;

    public VotesAdapter(List<Choice> votes, Context context) {
        this.choices = votes;
        this.context = context;
    }

    public void refreshList(List<Choice> choices, int totalVotes) {
        this.choices = choices;
        this.totalVotes = totalVotes;
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
        int percentage = calculatePercentage(choices.get(position).users.size());
        holder.voteTitle.setText(choices.get(position).value);
        holder.votePercentage.setText(percentage + "%");
        holder.progressBar.setProgress(percentage, true);
    }

    @Override
    public int getItemCount() {
        return choices.size();
    }

    private int calculatePercentage(int votes) {
        if (totalVotes != 0)
            return (votes * 100) / totalVotes;
        return 0;
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
