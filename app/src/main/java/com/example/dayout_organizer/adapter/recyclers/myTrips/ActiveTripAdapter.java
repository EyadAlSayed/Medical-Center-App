package com.example.dayout_organizer.adapter.recyclers.myTrips;

import android.content.Context;
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
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.photo.TripPhotoData;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.WarningDialog;
import com.example.dayout_organizer.ui.fragments.trips.FilterFragment;
import com.example.dayout_organizer.ui.fragments.trips.UpcomingTripDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;
import static com.example.dayout_organizer.viewModels.TripViewModel.TRIP_PHOTOS_URL;

public class ActiveTripAdapter extends RecyclerView.Adapter<ActiveTripAdapter.ViewHolder> {

    List<TripData> list;
    Context context;

    public ActiveTripAdapter(List<TripData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void refresh(List<TripData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActiveTripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_upcoming_trip_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveTripAdapter.ViewHolder holder, int position) {

        String tripStops = "";
        holder.title.setText(list.get(position).title);
        holder.description.setText(list.get(position).description);
        holder.date.setText(list.get(position).begin_date);
        holder.passengersCount.setText(String.valueOf(list.get(position).customer_trips.size()));
        holder.bindImageSlider(list.get(position).trip_photos);
        holder.deleteIcon.setVisibility(View.GONE);

        for (int i = 0; i < list.get(position).place_trips.size(); i++) {
            if (i != 0) {
                tripStops += ", " + list.get(position).place_trips.get(i).place.name;
            } else if (i == 0)
                tripStops += list.get(position).place_trips.get(i).place.name;
            ;
        }

        holder.stops = tripStops;
        holder.tripStops.setText(tripStops);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            init();
        }


        private void init() {
            deleteIcon.setOnClickListener(onDeleteClicked);
            activeTV.setVisibility(View.GONE);
        }

        private final View.OnClickListener onDeleteClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WarningDialog(context, "Are you sure you want to delete this trip?").show();
            }
        };

        @Override
        public void onClick(View v) {
            if (!FilterFragment.isFilterOpen) {
                TripData data = list.get(getAdapterPosition());
                data.stopsToDetails = stops;
                FN.addFixedNameFadeFragment(MAIN_FRC, (MainActivity) context, new UpcomingTripDetailsFragment(data));
            }
        }

        private void bindImageSlider(List<TripPhotoData> photos) {
            List<SlideModel> slideModels = new ArrayList<>();

            for (TripPhotoData ph : photos) {
                slideModels.add(new SlideModel(TRIP_PHOTOS_URL + ph.id
                        , ScaleTypes.FIT));
            }

            imageSlider.setImageList(slideModels);

            imageSlider.setScrollBarFadeDuration(10000);
        }

    }
}
