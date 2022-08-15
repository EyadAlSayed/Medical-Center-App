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
import android.util.Pair;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.poll.PollData;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.DeleteTripOrPollDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.ui.dialogs.notify.WarningDialog;
import com.example.dayout_organizer.ui.fragments.polls.*;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.ViewHolder> {

    List<PollData> polls;
    Context context;

    public PollsAdapter(List<PollData> polls, Context context){
        this.polls = polls;
        this.context = context;
    }

    public void refresh(List<PollData> polls){
        this.polls = polls;
        notifyDataSetChanged();
    }

    public void insertRoomObject(PollData pollData) {

        // insert object in room database
        ((MainActivity) context).iPoll
                .insertPoll(pollData)
                .subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    @NonNull
    @Override
    public PollsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_poll_item, parent, false);
        return new PollsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PollsAdapter.ViewHolder holder, int position) {
        insertRoomObject(polls.get(position));
        holder.pollTitle.setText(polls.get(position).title);
        holder.description.setText(polls.get(position).description);
    }

    @Override
    public int getItemCount() {
        return polls.size();
    }

    @SuppressLint("NonConstantResourceId")
    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.poll_title)
        TextView pollTitle;

        @BindView(R.id.poll_delete_button)
        ImageButton deleteButton;

        @BindView(R.id.poll_description)
        TextView description;

        @BindView(R.id.poll_votes_button)
        Button votesButton;

        LoadingDialog loadingDialog;
        DeleteTripOrPollDialog deleteTripOrPollDialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            initViews();
        }

        private void initViews(){
            loadingDialog = new LoadingDialog(context);
            deleteTripOrPollDialog = new DeleteTripOrPollDialog(context,context.getResources().getString(R.string.deleting_poll));
            deleteButton.setOnClickListener(onDeleteClicked);
            votesButton.setOnClickListener(onVotesClicked);
        }

        private final View.OnClickListener onDeleteClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTripOrPollDialog.setWarningDialogYes(onYesClicked);
                deleteTripOrPollDialog.show();
            }
        };

        private final View.OnClickListener onYesClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TripViewModel.getINSTANCE().deletePolls(polls.get(getAdapterPosition()).id);
                TripViewModel.getINSTANCE().successfulMutableLiveData.observe((MainActivity)context,successfulObserver);
            }
        };

        private final Observer<Pair<Boolean,String>> successfulObserver = new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> booleanStringPair) {
                    loadingDialog.dismiss();
                    if (booleanStringPair != null) {
                        if (booleanStringPair.first != null) {
                            deleteTripOrPollDialog.dismiss();
                            notifyItemRemoved(getAdapterPosition());
                            NoteMessage.showSnackBar((MainActivity) context, context.getResources().getString(R.string.successfully_deleted));
                        }
                    } else
                        NoteMessage.showSnackBar((MainActivity) context, context.getString(R.string.cant_be_deleted));
            }
        };

        private final View.OnClickListener onVotesClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PollData poll = polls.get(getAdapterPosition());
                FN.addFixedNameFadeFragment(MAIN_FRC, (MainActivity) context, new VotesListFragment(poll.choices));
            }
        };
    }
}
