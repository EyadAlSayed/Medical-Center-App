package com.example.dayout_organizer.ui.fragments.polls;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.poll.Choice;
import com.example.dayout_organizer.models.poll.PollData;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.ui.dialogs.SuccessDialog;
import com.example.dayout_organizer.ui.dialogs.WarningDialog;
import com.example.dayout_organizer.viewModels.PollViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class CreatePollFragment extends Fragment {

    View view;

    @BindView(R.id.new_poll_discard)
    TextView discard_TV;

    @BindView(R.id.new_poll_description)
    EditText description;

    @BindView(R.id.new_poll_options_layout)
    LinearLayout optionsLayout;

    @BindView(R.id.new_poll_add_option_button)
    Button addOptionButton;

    @BindView(R.id.new_poll_publish_button)
    Button publishButton;

    @BindView(R.id.new_poll_title)
    TextView title;

    LoadingDialog loadingDialog;

    //ArrayList<VoteData> options;
    ArrayList<Choice> options;

    @Override
    public void onStart() {
        ((MainActivity)requireActivity()).hideBottomBar();
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_poll, container, false);
        ButterKnife.bind(this, view);
        initViews();

        return view;
    }

    private void initViews() {
        loadingDialog = new LoadingDialog(requireContext());
        options = new ArrayList<>();
        discard_TV.setOnClickListener(onDiscardClicked);
        addOptionButton.setOnClickListener(onAddOptionClicked);
        publishButton.setOnClickListener(onPublishClicked);
    }

    private void createPoll() {
        loadingDialog.show();
        PollViewModel.getINSTANCE().createPoll(getNewPollData());
        PollViewModel.getINSTANCE().createPollMutableLiveData.observe(requireActivity(), pollObserver);
    }

    private final Observer<Pair<PollData, String>> pollObserver = new Observer<Pair<PollData, String>>() {
        @Override
        public void onChanged(Pair<PollData, String> pollDataStringPair) {
            loadingDialog.dismiss();
            if(pollDataStringPair != null){
                if(pollDataStringPair.first != null){
                    new SuccessDialog(requireContext(), "Poll Published!").show();
                    FN.popStack(requireActivity());
                } else
                    new ErrorDialog(requireContext(), pollDataStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

    private PollData getNewPollData(){
        PollData poll = new PollData();
        poll.title = title.getText().toString();
        poll.description = description.getText().toString();
        poll.choices = options;
        return poll;
    }

    private void removeView(View v){
        optionsLayout.removeView(v);
    }

    private boolean validPoll(){
        return hasTitle() && hasDescription() && validOptions();
    }

    private boolean hasDescription(){
        if(description.getText().toString().isEmpty()){
            NoteMessage.showSnackBar(requireActivity(), "Description cannot be empty.");
            return false;
        }
        return true;
    }

    private boolean hasTitle(){
        if(title.getText().toString().isEmpty()){
            NoteMessage.showSnackBar(requireActivity(), "Title cannot be empty");
            return false;
        }
        return true;
    }

    private boolean validOptions(){
        return !lessThanTwoOptions() && !hasEmptyOption();
    }

    private boolean lessThanTwoOptions(){
        if(optionsLayout.getChildCount() < 2){
            NoteMessage.showSnackBar(requireActivity(), "At least 2 options allowed.");
            return true;
        }
        return false;
    }

    private boolean hasEmptyOption(){
        for (int i = 0; i < optionsLayout.getChildCount(); i++){
            View view = optionsLayout.getChildAt(i);
            EditText title = (EditText) view.findViewById(R.id.single_option_title);
            if(title.getText().toString().isEmpty()){
                NoteMessage.showSnackBar(requireActivity(), "Cannot add an empty option.");
                return true;
            }
        }
        return false;
    }

    private final View.OnClickListener onDiscardClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new WarningDialog(requireContext(), "Are you sure you want to discard this poll?", true).show();
        }
    };

    private final View.OnClickListener onAddOptionClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final View optionView = getLayoutInflater().inflate(R.layout.single_poll_option, null, false);

            ImageButton deleteOptionButton = (ImageButton)optionView.findViewById(R.id.single_option_delete_icon);

            deleteOptionButton.setOnClickListener(v1 -> removeView(optionView));

            optionsLayout.addView(optionView);
        }
    };

    private final View.OnClickListener onPublishClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            options.clear();
            if(validPoll()){
                for(int i = 0; i < optionsLayout.getChildCount(); i++){

                    View optionView = optionsLayout.getChildAt(i);
                    EditText optionTitle = (EditText)optionView.findViewById(R.id.single_option_title);

                    //options.add(new VoteData(optionTitle.getText().toString(), 0));

                    options.add(new Choice(optionTitle.getText().toString()));
                }
                createPoll();
            }
        }
    };
}
