package com.example.dayout_organizer.adapter.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.fragments.trips.OldTripDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;

public class MyTripsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = "MyTripsAdapter";

    List<TripModel> list;
    Context context;
    int type;

    public MyTripsAdapter(List<TripModel> list, Context context) {
        this.context = context;
        this.list = list;
    }

    public void refreshList(List<TripModel> list, int type) {
        this.list = list;
        this.type = type;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_old_trip_layout, parent, false);
                return new ViewHolderOld(view);
            }
            case 2:

            case 3: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_upcoming_trip_layout, parent, false);
                return new ViewHolderUpcoming(view);
            }

            default: {
                Log.e(TAG, "onCreateViewHolder: " + "item type is null");
                return null;
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1: {
                ViewHolderOld viewHolder = (ViewHolderOld) holder;
                viewHolder.title.setText(list.get(position).title);
                viewHolder.description.setText(list.get(position).description);
                viewHolder.date.setText(list.get(position).date);
                viewHolder.passengersCount.setText(String.valueOf(list.get(position).passengers_count));
                viewHolder.bindImageSlider(list.get(position).photos);
                break;
            }

            case 2: {
                ViewHolderUpcoming viewHolder = (ViewHolderUpcoming) holder;
                viewHolder.title.setText(list.get(position).title);
                viewHolder.description.setText(list.get(position).description);
                viewHolder.date.setText(list.get(position).date);
                viewHolder.passengersCount.setText(String.valueOf(list.get(position).passengers_count));
                viewHolder.bindImageSlider(list.get(position).photos);
                break;
            }

            case 3: {
                System.out.println(type);
                ViewHolderUpcoming viewHolder = (ViewHolderUpcoming) holder;
                viewHolder.title.setText(list.get(position).title);
                viewHolder.description.setText(list.get(position).description);
                viewHolder.date.setText(list.get(position).date);
                viewHolder.passengersCount.setText(String.valueOf(list.get(position).passengers_count));
                viewHolder.bindImageSlider(list.get(position).photos);
                viewHolder.deleteIcon.setVisibility(View.GONE);
                viewHolder.activeTV.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // 1: Old
    // 2: Upcoming
    // 3: Active
    @Override
    public int getItemViewType(int position) {

        if (type == 1)
            return 1;
        else if (type == 2)
            return 2;
        else if (type == 3)
            return 3;

        return -1;
    }

    @SuppressLint("NonConstantResourceId")
    class ViewHolderOld extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.old_trip_title)
        TextView title;

        @BindView(R.id.old_trip_passengers_count)
        TextView passengersCount;

        @BindView(R.id.old_trip_date)
        TextView date;

        @BindView(R.id.old_trip_description)
        TextView description;

        @BindView(R.id.old_trip_image_slider)
        ImageSlider imageSlider;

        public ViewHolderOld(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            //TODO: Go to trip details - Caesar.
            FN.addFixedNameFadeFragment(MAIN_FRC, (MainActivity)context, new OldTripDetailsFragment());
        }

        private void bindImageSlider(List<String> photos) {
            List<SlideModel> slideModels = new ArrayList<>();

            //TODO: Set image list - Caesar.
        }
    }

    @SuppressLint("NonConstantResourceId")
    class ViewHolderUpcoming extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.upcoming_trip_title)
        TextView title;

        @BindView(R.id.upcoming_trip_delete_icon)
        ImageButton deleteIcon;

        @BindView(R.id.upcoming_trip_passengers_count)
        TextView passengersCount;

        @BindView(R.id.upcoming_trip_date)
        TextView date;

        @BindView(R.id.upcoming_trip_description)
        TextView description;

        @BindView(R.id.upcoming_trip_image_slider)
        ImageSlider imageSlider;

        @BindView(R.id.upcoming_trip_active_tv)
        TextView activeTV;

        public ViewHolderUpcoming(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            init();
        }

        private void init() {
            deleteIcon.setOnClickListener(onDeleteClicked);
            activeTV.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            //TODO: Go to trip details - Caesar.
            //FN.addFixedNameFadeFragment(, (MainActivity)context, );
        }

        private final View.OnClickListener onDeleteClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Delete Trip - Caesar.
            }
        };

        private void bindImageSlider(List<String> photos) {
            List<SlideModel> slideModels = new ArrayList<>();

            //TODO: Set image list - Caesar.
        }
    }
}
