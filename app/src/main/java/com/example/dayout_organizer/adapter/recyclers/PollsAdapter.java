package com.example.dayout_organizer.adapter.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.poll.PollData;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.fragments.polls.*;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.ViewHolder> {

    List<PollData> polls;
    Context context;

    public PollsAdapter(List<PollData> polls, Context context){
        this.polls = polls;
        this.context = context;
    }

    public void refreshList(List<PollData> polls){
        this.polls = polls;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PollsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_poll_item, parent, false);
        return new PollsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PollsAdapter.ViewHolder holder, int position) {
        holder.description.setText(polls.get(position).description);
    }

    @Override
    public int getItemCount() {
        return polls.size();
    }

    @SuppressLint("NonConstantResourceId")
    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.poll_delete_button)
        ImageButton deleteButton;

        @BindView(R.id.poll_description)
        TextView description;

        @BindView(R.id.poll_votes_button)
        Button votesButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            initViews();
        }

        private void initViews(){
            deleteButton.setOnClickListener(onDeleteClicked);
            votesButton.setOnClickListener(onVotesClicked);
        }

        private final View.OnClickListener onDeleteClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        private final View.OnClickListener onVotesClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FN.addFixedNameFadeFragment(MAIN_FRC, (MainActivity) context, new VotesListFragment());
            }
        };
    }
}
