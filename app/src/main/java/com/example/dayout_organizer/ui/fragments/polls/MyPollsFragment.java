package com.example.dayout_organizer.ui.fragments.polls;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.PollsAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.poll.PollData;
import com.example.dayout_organizer.models.poll.PollPaginationModel;
import com.example.dayout_organizer.room.pollRoom.database.PollDataBase;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.viewModels.PollViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.page_loading_pbar)
    ProgressBar pageLoadingBar;

    LoadingDialog loadingDialog;

    PollsAdapter adapter;

    List<PollData> mainList;
    List<PollData> filteredList;

    int pageIndex;
    boolean canPaginate;


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
        ((MainActivity) requireActivity()).hideBottomBar();
        super.onStart();
    }

    private void initViews() {
        loadingDialog = new LoadingDialog(requireContext());
        pageIndex = 1;
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        mainList = new ArrayList<>();
        filteredList = new ArrayList<>();

        initRecycler();

        pollsBackButton.setOnClickListener(onBackClicked);
        pollsAddButton.setOnClickListener(onAddClicked);
        searchField.addTextChangedListener(textWatcher);
    }

    private void initRecycler() {
        pollsRecycler.setHasFixedSize(true);
        pollsRecycler.addOnScrollListener(onRcScrolled);
        pollsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PollsAdapter(new ArrayList<>(), requireContext());
        pollsRecycler.setAdapter(adapter);
    }

    private void getDataFromAPI() {
        loadingDialog.show();
        PollViewModel.getINSTANCE().getPolls(pageIndex);
        PollViewModel.getINSTANCE().pollsMutableLiveData.observe(requireActivity(), pollsObserver);
    }

    private void filter(String pollTitle) {
        filteredList.clear();

        if (pollTitle.isEmpty()) {
            adapter.refresh(mainList);
            return;
        }

        for (PollData poll : mainList) {
            if (poll.title.toLowerCase().contains(pollTitle)) {
                filteredList.add(poll);
            }
        }

        adapter.refresh(filteredList);
    }

    private final Observer<Pair<PollPaginationModel, String>> pollsObserver = new Observer<Pair<PollPaginationModel, String>>() {
        @Override
        public void onChanged(Pair<PollPaginationModel, String> pollDataStringPair) {
            loadingDialog.dismiss();
            hideLoadingBar();
            if (pollDataStringPair != null) {
                if (pollDataStringPair.first != null) {
                    mainList = pollDataStringPair.first.data.data;
                    adapter.addAndRefresh(pollDataStringPair.first.data.data);
                    canPaginate = (pollDataStringPair.first.data.next_page_url != null);
                } else{
                    getDataFromRoom();
                    canPaginate =false;
                    new ErrorDialog(requireContext(), pollDataStringPair.second).show();
                }

            } else
            {
                getDataFromRoom();
                canPaginate =false;
                new ErrorDialog(requireContext(), "Error Connection").show();
            }

        }
    };

    private final View.OnClickListener onBackClicked = v -> {
        FN.popStack(requireActivity());
    };

    private final View.OnClickListener onAddClicked = v -> {
        FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new CreatePollFragment());
    };

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

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            swipeRefreshLayout.setEnabled(false);
            getDataFromAPI();
        }
    };

    private void getDataFromRoom() {
        PollDataBase.getINSTANCE(requireContext())
                .iPoll()
                .getPollsData()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<PollData>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull List<PollData> pollData) {
                        adapter.refresh(pollData);
                        mainList = pollData;
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }
                });
    }


    // pagination method

    private void hideLoadingBar() {
        if (pageLoadingBar.getVisibility() == View.GONE) return;

        pageLoadingBar.animate().setDuration(400).alpha(0);
        new Handler(Looper.getMainLooper()).postDelayed(() -> pageLoadingBar.setVisibility(View.GONE), 450);
    }

    private void showLoadingBar() {
        if (pageLoadingBar.getVisibility() == View.VISIBLE) return;

        pageLoadingBar.setAlpha(1);
        pageLoadingBar.setVisibility(View.VISIBLE);
    }

    private final RecyclerView.OnScrollListener onRcScrolled = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState == 1 && canPaginate) {    // is scrolling
                pageIndex++;
                showLoadingBar();
                getDataFromAPI();
                canPaginate = false;
            }

            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);
        }
    };
}
