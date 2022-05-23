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
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.WarningDialog;
import com.example.dayout_organizer.ui.fragments.trips.EditTrip.EditTripFragment;
import com.example.dayout_organizer.ui.fragments.trips.FilterFragment;
import com.example.dayout_organizer.ui.fragments.trips.OldTripDetailsFragment;
import com.example.dayout_organizer.ui.fragments.trips.UpcomingTripDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;
import static com.example.dayout_organizer.viewModels.TripViewModel.TRIP_PHOTOS_URL;

public class MyTripsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = "MyTripsAdapter";

    List<TripModel.Data> list;
    Context context;
    int type;

    public MyTripsAdapter(List<TripModel.Data> list, Context context) {
        this.context = context;
        this.list = list;
    }

    public void refreshList(ArrayList<TripModel.Data> list, int type) {
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
        String tripStops = "";

        switch (holder.getItemViewType()) {
            case 1: {
                ViewHolderOld viewHolder = (ViewHolderOld) holder;
                viewHolder.title.setText(list.get(position).title);
                viewHolder.description.setText(list.get(position).description);
                viewHolder.date.setText(list.get(position).begin_date);
                viewHolder.passengersCount.setText(String.valueOf(list.get(position).customer_trips_count));
                viewHolder.bindImageSlider(list.get(position).trip_photos);

                for(int i = 0; i < list.get(position).place_trips.size(); i++){
                    if (i != 0) {
                        tripStops += ", " + list.get(position).place_trips.get(i).place.name;
                    } else if(i == 0)
                        tripStops += list.get(position).place_trips.get(i).place.name;;
                }

                viewHolder.stops = tripStops;
                viewHolder.tripStops.setText(tripStops);

                break;
            }

            case 2: {

                ViewHolderUpcoming viewHolder = (ViewHolderUpcoming) holder;
                viewHolder.title.setText(list.get(position).title);
                viewHolder.description.setText(list.get(position).description);
                viewHolder.date.setText(list.get(position).begin_date);
                viewHolder.passengersCount.setText(String.valueOf(list.get(position).customer_trips_count));
                viewHolder.bindImageSlider(list.get(position).trip_photos);

                for(int i = 0; i < list.get(position).place_trips.size(); i++){
                    if (i != 0) {
                        tripStops += ", " + list.get(position).place_trips.get(i).place.name;
                    } else if(i == 0)
                        tripStops += list.get(position).place_trips.get(i).place.name;;
                }

                viewHolder.stops = tripStops;
                viewHolder.tripStops.setText(tripStops);
            }

            case 3: {
                ViewHolderUpcoming viewHolder = (ViewHolderUpcoming) holder;
                viewHolder.title.setText(list.get(position).title);
                viewHolder.description.setText(list.get(position).description);
                viewHolder.date.setText(list.get(position).begin_date);
                viewHolder.passengersCount.setText(String.valueOf(list.get(position).customer_trips_count));
                viewHolder.bindImageSlider(list.get(position).trip_photos);
                viewHolder.deleteIcon.setVisibility(View.GONE);
                viewHolder.activeTV.setVisibility(View.VISIBLE);

                for(int i = 0; i < list.get(position).place_trips.size(); i++){
                    if (i != 0) {
                        tripStops += ", " + list.get(position).place_trips.get(i).place.name;
                    } else if(i == 0)
                        tripStops += list.get(position).place_trips.get(i).place.name;;
                }

                viewHolder.stops = tripStops;
                viewHolder.tripStops.setText(tripStops);
                break;
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

        @BindView(R.id.old_trip_stops)
        TextView tripStops;

        String stops;

        public ViewHolderOld(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if (!FilterFragment.isFilterOpen) {
                TripModel.Data data = list.get(getAdapterPosition());
                data.stopsToDetails = stops;
                FN.addFixedNameFadeFragment(MAIN_FRC, (MainActivity) context, new OldTripDetailsFragment(data));
            }
        }

        private void bindImageSlider(List<TripModel.TripPhoto> photos) {
            List<SlideModel> slideModels = new ArrayList<>();

            for (TripModel.TripPhoto ph : photos) {
                slideModels.add(new SlideModel(TRIP_PHOTOS_URL + ph.id
                        , ScaleTypes.FIT));
            }

            imageSlider.setImageList(slideModels);

            imageSlider.setScrollBarFadeDuration(10000);
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

        @BindView(R.id.upcoming_trip_stops)
        TextView tripStops;

        String stops;

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
            if (!FilterFragment.isFilterOpen) {
                TripModel.Data data = list.get(getAdapterPosition());
                data.stopsToDetails = stops;
                FN.addFixedNameFadeFragment(MAIN_FRC, (MainActivity) context, new UpcomingTripDetailsFragment(data));
            }
        }

        private final View.OnClickListener onDeleteClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WarningDialog(context, "Are you sure you want to delete this trip?").show();
            }
        };

        private void bindImageSlider(List<TripModel.TripPhoto> photos) {
            List<SlideModel> slideModels = new ArrayList<>();

            for (TripModel.TripPhoto ph : photos) {
                slideModels.add(new SlideModel(TRIP_PHOTOS_URL + ph.id
                        , ScaleTypes.FIT));
            }

            imageSlider.setImageList(slideModels);

            imageSlider.setScrollBarFadeDuration(10000);
        }
    }
}
