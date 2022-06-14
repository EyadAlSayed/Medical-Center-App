package com.example.dayout_organizer.ui.fragments.polls;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.PollsAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.poll.PollData;
import com.example.dayout_organizer.models.poll.PollPaginationModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.viewModels.PollViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

@SuppressLint("NonConstantResourceId")
public class MyPollsFragment extends Fragment {

    View view;

    @BindView(R.id.polls_back_button)
    ImageButton pollsBackButton;

    @BindView(R.id.polls_recycler)
    RecyclerView pollsRecycler;

    @BindView(R.id.polls_add_button)
    FloatingActionButton pollsAddButton;

    @BindView(R.id.polls_search_field)
    EditText searchField;

    LoadingDialog loadingDialog;

    PollsAdapter adapter;

    List<PollData> mainList;
    List<PollData> filteredList;

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
        loadingDialog = new LoadingDialog(requireContext());
        mainList = new ArrayList<>();
        filteredList = new ArrayList<>();
        initRecycler();
        pollsBackButton.setOnClickListener(onBackClicked);
        pollsAddButton.setOnClickListener(onAddClicked);
        searchField.addTextChangedListener(textWatcher);
    }

    private void initRecycler(){
        pollsRecycler.setHasFixedSize(true);
        pollsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PollsAdapter(new ArrayList<>(), requireContext());
        pollsRecycler.setAdapter(adapter);
    }

    private void getDataFromAPI(){
        loadingDialog.show();
        PollViewModel.getINSTANCE().getPolls();
        PollViewModel.getINSTANCE().pollsMutableLiveData.observe(requireActivity(), pollsObserver);
    }

    private void filter(String pollTitle){
        filteredList.clear();

        if(pollTitle.isEmpty()){
            adapter.refreshList(mainList);
            return;
        }

        for(PollData poll: mainList){
            if(poll.title.toLowerCase().contains(pollTitle)){
                filteredList.add(poll);
            }
        }

        adapter.refreshList(filteredList);
    }

    private final Observer<Pair<PollPaginationModel, String>> pollsObserver = new Observer<Pair<PollPaginationModel, String>>() {
        @Override
        public void onChanged(Pair<PollPaginationModel, String> pollDataStringPair) {
            loadingDialog.dismiss();
            if(pollDataStringPair != null){
                if(pollDataStringPair.first != null){
                    mainList = pollDataStringPair.first.data.data;
                    adapter.refreshList(pollDataStringPair.first.data.data);
                } else
                    new ErrorDialog(requireContext(), pollDataStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection").show();
        }
    };

    private final View.OnClickListener onBackClicked = v -> {FN.popStack(requireActivity());};

    private final View.OnClickListener onAddClicked = v -> {FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new CreatePollFragment());};

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            filter(s.toString().toLowerCase());
        }
    };
}
